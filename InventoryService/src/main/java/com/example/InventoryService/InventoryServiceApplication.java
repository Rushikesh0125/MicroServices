package com.example.InventoryService;

import com.example.InventoryService.Model.Inventory;
import com.example.InventoryService.Repository.InventryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventryRepository inventryRepository){
		return  args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Iphone-13");
			inventory1.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Iphone-14");
			inventory2.setQuantity(0);

			inventryRepository.save(inventory1);
			inventryRepository.save(inventory2);

		};
	}

}
