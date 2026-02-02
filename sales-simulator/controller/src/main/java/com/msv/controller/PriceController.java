package com.msv.controller;

import com.msv.application.port.PriceService;
import com.msv.application.entity.PriceRequest;
import com.msv.controller.api.DefaultApi;
import com.msv.controller.mapper.PriceControllerMapper;
import com.msv.controller.model.PriceResponse;
import com.msv.domain.entity.Price;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
@AllArgsConstructor
public class PriceController implements DefaultApi{

    private PriceService priceService;
    private PriceControllerMapper mapper;

    @Override
    public ResponseEntity<PriceResponse> getSales(
            OffsetDateTime applicationDate,
            String productId,
            String chainId
    ){
        if(applicationDate == null || productId == null || chainId == null)
        {
            return ResponseEntity.badRequest().build();
        }
        try {
            Price price = priceService.getPrice(PriceRequest.builder()
                    .applicationDate(applicationDate.toLocalDateTime())
                    .productId(productId)
                    .chainId(chainId)
                    .build());
            return ResponseEntity.ok(mapper.toResponse(price));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
