package com.msv.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sales")
@NoArgsConstructor
public class SalesController {

    @GetMapping
    public ResponseEntity<String> getSales(){
        return ResponseEntity.ok("Sales");
    }
}
