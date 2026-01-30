package com.msv.application.port;

import com.msv.application.entity.PriceRequest;
import com.msv.domain.entity.Price;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    Price getPrice(PriceRequest request) throws Exception;
}
