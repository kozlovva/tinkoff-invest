package ru.kozlovva.investservice.service;

import ru.tinkoff.invest.openapi.models.Currency;

import java.math.BigDecimal;

public interface ICurrencyRateService {
    BigDecimal getToRubRate(Currency currency);
}
