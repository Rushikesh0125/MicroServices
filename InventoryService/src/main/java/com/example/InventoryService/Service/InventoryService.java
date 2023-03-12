package com.example.InventoryService.Service;

import com.example.InventoryService.Repository.InventryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventryRepository inventryRepository;

    @Transactional(readOnly = true)
    public boolean checkStock(String skuCode){
        return inventryRepository.findBySkuCode(skuCode).isPresent();
    }

}
