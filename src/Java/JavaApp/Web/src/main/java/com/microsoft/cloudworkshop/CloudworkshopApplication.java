package com.microsoft.cloudworkshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;

@SpringBootApplication
@Controller
public class CloudworkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudworkshopApplication.class, args);
	}

	@GetMapping(value = "/")
	public String getProducts() throws Exception {
		try (DaprClient client = (new DaprClientBuilder()).build()) {
			var response = client
					.invokeMethod("backend-api", "api/Product", null, HttpExtension.GET, null, String.class)
					.block();
			System.out.println(response);
		}
		return "/index";
	}

}
