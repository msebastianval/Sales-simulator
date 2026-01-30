package com.msv.controller;

import com.msv.application.port.PriceService;
import com.msv.application.entity.PriceRequest;
import com.msv.controller.entity.PriceResponse;
import com.msv.controller.mapper.PriceControllerMapper;
import com.msv.domain.entity.Price;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/sales")
@AllArgsConstructor
public class PriceController {

    private PriceService priceService;
    private PriceControllerMapper mapper;

    @GetMapping
    public ResponseEntity<PriceResponse> getSales(
            @RequestParam LocalDateTime applicationDate,
            @RequestParam String productId,
            @RequestParam String chainId
    ){
        if(applicationDate == null || productId == null || chainId == null)
        {
            return ResponseEntity.badRequest().build();
        }
        try {
            Price price = priceService.getPrice(PriceRequest.builder()
                    .applicationDate(applicationDate)
                    .productId(productId)
                    .chainId(chainId)
                    .build());
            return ResponseEntity.ok(mapper.toResponse(price));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
