package com.example.InventoryService.controller;

import com.example.InventoryService.Dto.InventoryResponse;
import com.example.InventoryService.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class Inventorycontroller {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@PathVariable("skuCode") List<String> skuCode){
        return inventoryService.checkStock(skuCode);
    }
}
