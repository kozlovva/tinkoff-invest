package ru.kozlovva.investservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kozlovva.investservice.dao.BalanceInfo;
import ru.kozlovva.investservice.dao.BalanceRepository;
import ru.kozlovva.investservice.service.IBalanceService;
import ru.kozlovva.investservice.service.ICurrencyRateService;
import ru.kozlovva.investservice.service.IPriceService;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@RequiredArgsConstructor
@Service
public class BalanceService implements IBalanceService {

    private final OpenApi api;
    private final ICurrencyRateService currencyRateService;
    private final BalanceRepository balanceRepository;
    private final IPriceService priceService;

    //Вернуть баланс в рублях
    public BigDecimal calcBalance(String brokerAccountId) {
        var freeMoney = calcFreeMoneyBalance(brokerAccountId);
        var activeMoney = calcActiveMoneyBalance(brokerAccountId);
        var total = freeMoney.add(activeMoney);
        log.debug("\n" +
                "Свободные деньги {} руб " +
                "\nДеньги в бумагах {} руб " +
                "\nИтого {} руб", freeMoney, activeMoney, total);
        balanceRepository.save(BalanceInfo.create(freeMoney, activeMoney, total));
        return total;
    }

    private BigDecimal calcActiveMoneyBalance(String brokerAccountId) {
        var resultByCurrency = new ArrayList<BigDecimal>();
        api.getPortfolioContext().getPortfolio(brokerAccountId).join()
                .positions
                .stream()
                .collect(groupingBy(p -> p.averagePositionPrice.currency))
                .forEach((c, l) -> {
                    List<BigDecimal> totals = l.stream()
                            .map(p -> {
                                var actualPrice = priceService.getMarketValue(p.figi);
                                return Objects.requireNonNull(actualPrice).multiply(BigDecimal.valueOf(p.lots));
                            })
                            .collect(Collectors.toList());
                    resultByCurrency.add(calcInRub(c, totals));
                });
        return sum(resultByCurrency);
    }

    private BigDecimal calcFreeMoneyBalance(String brokerAccountId) {
        var resultByCurrency = new ArrayList<BigDecimal>();
        api.getPortfolioContext().getPortfolioCurrencies(brokerAccountId).join().currencies
                .stream()
                .collect(groupingBy(p -> p.currency))
                .forEach((c, l) -> {
                    List<BigDecimal> values = l
                            .stream()
                            .map(p -> p.balance)
                            .collect(Collectors.toList());
                    resultByCurrency.add(calcInRub(c, values));
                });
        return sum(resultByCurrency);
    }

    private BigDecimal calcInRub(Currency c, List<BigDecimal> values) {
        var rate = currencyRateService.getToRubRate(c);
        return values.stream()
                .map(v -> v.multiply(rate))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal sum(List<BigDecimal> values) {
        return values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
