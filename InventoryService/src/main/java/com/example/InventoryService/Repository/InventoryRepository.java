package com.example.InventoryService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.InventoryService.Model.Inventory;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
