package com.microsoft.cloudworkshop;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudworkshopApplication {

	public static void main(String[] args) {
		System.out.println("The program has started.");
		System.out.println("Using Thread.sleep() to add 10 second delay (" + java.time.LocalDateTime.now() + ")");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Time after wait: " + java.time.LocalDateTime.now());
		System.out.println("The program has ended.");
	}

}