package com.rebalance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ConnectivityTestController {

    @GetMapping("/connect/test")
    public ResponseEntity<String> testConnection() {
        return new ResponseEntity<>("{\"connection\": \"connected\"}", HttpStatus.OK);
    }

}
