package com.nibble.demo.orders.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderIdResource {
    private static Random random = new Random();
    @GetMapping(value="orderId", produces = MediaType.APPLICATION_JSON_VALUE )
    public String orderId(){
        try {
            Thread.sleep(random.nextInt(1000)); //Random sleep upto 1 sec
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return UUID.randomUUID().toString();
    }
}