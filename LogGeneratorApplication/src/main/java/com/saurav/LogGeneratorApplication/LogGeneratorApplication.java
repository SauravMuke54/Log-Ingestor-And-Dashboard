package com.saurav.LogGeneratorApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saurav.LogGeneratorApplication.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogGeneratorApplication implements CommandLineRunner {

	@Autowired
	private ProducerService logger;

	public static void main(String[] args) {
		SpringApplication.run(LogGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {

		System.out.println("Generating sample logs");
		logger.printLog("User login successful for userId=1023", "INFO", "Auth Service");
		logger.printLog("Fetching user roles from cache", "DEBUG", "Auth Service");
		logger.printLog("Database connection established", "INFO", "Billing Service");
		logger.printLog("Payment request initiated for orderId=9832", "INFO", "Payment Service");
		logger.printLog("Invoice generation took 145ms", "DEBUG", "Billing Service");
		logger.printLog("Low stock detected for item SKU=SK123", "WARN", "Inventory Service");
		logger.printLog("Email notification queued for user john@example.com", "INFO", "Notification Service");
		logger.printLog("SMTP connection timeout — retrying...", "WARN", "Notification Service");
		logger.printLog("Cache miss for key=user_profile_1023", "DEBUG", "Auth Service");
		logger.printLog("Payment gateway returned HTTP 500 for transaction T9843", "ERROR", "Payment Service");
		logger.printLog("Retrying order confirmation for orderId=7643", "WARN", "Order Service");
		logger.printLog("New customer record created with ID=C872", "INFO", "User Service");
		logger.printLog("Failed to parse JSON payload for webhook", "ERROR", "Integration Service");
		logger.printLog("Inventory updated successfully for warehouse=WH02", "INFO", "Inventory Service");
		logger.printLog("Fetching latest FX rates from external API", "DEBUG", "Billing Service");
		logger.printLog("Notification sent successfully via SMS provider", "INFO", "Notification Service");
		logger.printLog("Background job scheduler started", "INFO", "Core Service");
		logger.printLog("Job 'CleanupTempFiles' executed in 2.3s", "DEBUG", "Core Service");
		logger.printLog("Unexpected response format from partner API", "WARN", "Integration Service");
		logger.printLog("Database transaction rolled back due to constraint violation", "ERROR", "Billing Service");


	}
}
