package com.msv.application.port;

import com.msv.application.entity.PriceRequest;
import com.msv.domain.entity.Price;
public interface PriceService {

    Price getPrice(PriceRequest request);
}
