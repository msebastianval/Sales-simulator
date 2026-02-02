package com.msv.application.adapter;


import com.msv.application.entity.PriceRequest;
import com.msv.application.mapper.PriceApplicationMapper;
import com.msv.application.port.PriceService;
import com.msv.domain.entity.Price;
import com.msv.domain.repository.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final PriceApplicationMapper mapper;

    @Override
    public Price getPrice(PriceRequest request) throws Exception {
        Price price = priceRepository.getPrice(mapper.toCmd(request));
        if(price == null) throw new Exception();
        return price;
    }

}
