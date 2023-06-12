package com.microsoft.cloudworkshop;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class CloudworkshopApplication {
	private static final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");

	public static void main(String[] args) {
		SpringApplication.run(CloudworkshopApplication.class, args);
	}

	@GetMapping(value = "/")
	public void getProducts() throws Exception {
		String dapr_url = "http://localhost:" + DAPR_HTTP_PORT + "/api/Product";
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(dapr_url))
				.header("Content-Type", "application/json")
				.header("dapr-app-id", "backend-api")
				.build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
	}

}
