package ru.kozlovva.investservice.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.tinkoff.invest.openapi.models.Currency;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

public interface IPriceService {
    BigDecimal getMarketValue(String figi);

    @AllArgsConstructor
    @Getter
    enum CurrencyWithInfo {

        EUR(Currency.EUR, "BBG0013HJJ31", "EUR_RUB__TOM"),
        USD(Currency.USD, "BBG0013HGFT4", "USD000UTSTOM"),
        RUB(Currency.RUB, "", "");

        private  Currency currency;
        private String figi;
        private String tiker;

        public static Optional<CurrencyWithInfo> getByCurrency(Currency currency) {
            return Stream.of(CurrencyWithInfo.values())
                    .filter(c -> c.getCurrency().name().equals(currency.name()))
                    .findFirst();
        }
    }
}
