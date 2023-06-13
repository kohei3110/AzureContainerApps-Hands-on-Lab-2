package com.microsoft.cloudworkshop;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.cloudworkshop.models.Product;

@SpringBootApplication
@Controller
public class CloudworkshopApplication {
	private static final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");

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

}
