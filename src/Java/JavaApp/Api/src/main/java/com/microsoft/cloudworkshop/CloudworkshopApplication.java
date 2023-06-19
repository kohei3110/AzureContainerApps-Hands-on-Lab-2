package com.microsoft.cloudworkshop;

import java.util.ArrayList;
import java.util.List;

import io.dapr.Topic;
import io.dapr.springboot.annotations.BulkSubscribe;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.cloudworkshop.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.cloudworkshop.ProductRepository;

@SpringBootApplication
@RestController
public class CloudworkshopApplication {

	private final ProductRepository productRepository;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public CloudworkshopApplication(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(CloudworkshopApplication.class, args);
	}

	@GetMapping("/api/Product")
	public List<Product> getProduct() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		System.out.println("request received.");
		return products;
	}

	@BulkSubscribe
	@Topic(name = "topic", pubsubName = "${pubsubName:pubsub-servicebus}")
	@PostMapping("/api/Order")
	public Mono<Void> postOrder(@RequestBody(required = false) String event) {
		System.out.println("request received.");
		return Mono.fromRunnable(() -> {
			try {
				System.out.println("Subscriber got: " + event);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		})
				.then(
						Mono.fromRunnable(() -> {
							System.out.println("Requesting...");
						}));
	}
}