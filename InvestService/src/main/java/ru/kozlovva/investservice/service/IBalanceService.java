package ru.kozlovva.investservice.service;

import java.math.BigDecimal;

public interface IBalanceService {
    BigDecimal calcBalance(String brokerAccountId);
}
