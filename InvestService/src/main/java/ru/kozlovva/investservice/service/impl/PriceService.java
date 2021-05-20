package ru.kozlovva.investservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kozlovva.investservice.service.IPriceService;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.market.Orderbook;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PriceService implements IPriceService {

    private final OpenApi api;

    public BigDecimal getMarketValue(String figi) {
        Optional<Orderbook> orderbook = api.getMarketContext().getMarketOrderbook(figi, 1).join();
        if (orderbook.isEmpty())
            return BigDecimal.ZERO;
        if (orderbook.get().asks.isEmpty())
            return orderbook.get().lastPrice;

        return orderbook
                .get()
                .asks.get(0)
                .price;
    }
}
