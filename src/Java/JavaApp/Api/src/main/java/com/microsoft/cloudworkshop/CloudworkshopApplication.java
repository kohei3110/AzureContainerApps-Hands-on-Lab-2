package com.microsoft.cloudworkshop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.cloudworkshop.models.Product;

@SpringBootApplication
@RestController
public class CloudworkshopApplication {

	private final ProductRepository productRepository;

	public CloudworkshopApplication(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudworkshopApplication.class, args);
	}

	@GetMapping("/api/Product")
	public List<Product> getProduct() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
}
