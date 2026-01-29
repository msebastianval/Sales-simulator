package com.msv.application;

import com.msv.application.entity.PriceRequest;
import com.msv.domain.Price;
import org.springframework.stereotype.Service;

@Service
public interface SalesService{

    Price getPrice(PriceRequest request) throws Exception;
}
