package com.msv.application.adapter;


import com.msv.application.entity.PriceRequest;
import com.msv.application.mapper.PriceApplicationMapper;
import com.msv.application.port.PriceService;
import com.msv.domain.entity.Price;
import com.msv.domain.repository.PriceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final PriceApplicationMapper mapper;

    @Override
    public Price getPrice(PriceRequest request) {
        return priceRepository.getPrice(mapper.toCmd(request));
    }

}
