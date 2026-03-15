package com.nibble.demo.orders.controller;

import com.nibble.demo.orders.Inventory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/")
public class InventoryResource {

    private static final Random random = new Random();

    @PostMapping(value = "inventory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateInventory(@RequestBody Inventory inventory) {
        try {
            Thread.sleep(random.nextInt(1000)); //Random sleep upto 1 sec
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(true, HttpStatus.valueOf((200)));
    }
}