package ru.kozlovva.investservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kozlovva.investservice.service.ICurrencyRateService;
import ru.kozlovva.investservice.service.IPriceService;
import ru.tinkoff.invest.openapi.models.Currency;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyRateService implements ICurrencyRateService {

    private final IPriceService priceService;

    public BigDecimal getToRubRate(Currency currency) {
        if (currency.equals(Currency.RUB))
            return new BigDecimal(1);
        var currencyWithInfo = IPriceService.CurrencyWithInfo.getByCurrency(currency)
                .orElseThrow(() -> new RuntimeException("Валюта не найдена"));
        return priceService.getMarketValue(currencyWithInfo.getFigi());
    }


}
