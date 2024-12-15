package com.hulutas.inventory_service.initializer;

import com.hulutas.inventory_service.model.Inventory;
import com.hulutas.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository repository;

    @Override
    public void run(String... args) throws Exception {
            if (repository.count() == 0) {
                List<Inventory> inventories = new ArrayList<Inventory>(Arrays.asList(
                        new Inventory("P001", 5,false),
                        new Inventory("P002", 10,false),
                        new Inventory("P003", 15,false),
                        new Inventory("P004", 20,false),
                        new Inventory("P005", 25,false),
                        new Inventory("P006", 0,true)
                ));

                repository.saveAll(inventories);

                System.out.println("Init data added to mongo db.");
            }
    }
}
