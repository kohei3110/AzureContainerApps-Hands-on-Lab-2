package com.microsoft.cloudworkshop;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static java.util.Collections.singletonMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.cloudworkshop.models.Product;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.DaprPreviewClient;
import io.dapr.client.domain.BulkPublishResponse;
import io.dapr.client.domain.BulkPublishResponseFailedEntry;
import io.dapr.client.domain.CloudEvent;
import io.dapr.client.domain.Metadata;

@SpringBootApplication
@Controller
public class CloudworkshopApplication {
	private static final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");
	private static final String MESSAGE_TTL_IN_SECONDS = "1000";
	private static final int NUM_MESSAGES = 10;

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(CloudworkshopApplication.class, args);
	}

	@GetMapping(value = "/")
	public String getProducts(Model model) throws Exception {
		String dapr_url = "http://localhost:" + DAPR_HTTP_PORT + "/api/Product";
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(dapr_url))
				.header("Content-Type", "application/json")
				.header("dapr-app-id", "backend-api")
				.build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper mapper = new ObjectMapper();
		List<Product> products = mapper.readValue(response.body(), List.class);
		model.addAttribute("productlist", products);
		return "index";
	}

	// Publish a message to a topic
	@PostMapping(value = "/Orders")
	public void postOrder(@RequestBody Object message) throws Exception {
		try (DaprClient client = (new DaprClientBuilder()).build()) {
			for (int i = 0; i < NUM_MESSAGES; i++) {
				CloudEvent<String> cloudEvent = new CloudEvent<String>();
				cloudEvent.setId(UUID.randomUUID().toString());
				cloudEvent.setType("example");
				cloudEvent.setSpecversion("1");
				cloudEvent.setDatacontenttype("text/plain");
				cloudEvent.setData(String.format("This is order #%d", i));
				client.publishEvent("pubsub-servicebus", "topic", cloudEvent,
						singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
			}
		}
	}

	// Bulk Publish messages to a topic
	@PostMapping(value = "/BulkOrders")
	public void bulkPostOrder(@RequestBody Object message) throws Exception {
		try (DaprPreviewClient client = (new DaprClientBuilder()).buildPreviewClient()) {
			DaprClient c = (DaprClient) client;
			c.waitForSidecar(1000);
			try {
				List<String> messages = new ArrayList<>();
				for (int i = 0; i < NUM_MESSAGES; i++) {
					String msg = String.format("This is message #%d", i);
					messages.add(msg);
					System.out.println("Going to publish message : " + msg);
				}
				BulkPublishResponse<?> res = client.publishEvents("pubsub-servicebus", "topic", "text/plain", messages)
						.block();
				if (res != null) {
					if (res.getFailedEntries().size() > 0) {
						System.out.println("Some events failed to be published");
						for (BulkPublishResponseFailedEntry entry : res.getFailedEntries()) {
							System.out.println(" Error message : " + entry.getErrorMessage());
						}
					}
				}
			} finally {
				c.close();
			}
		}
	}

}