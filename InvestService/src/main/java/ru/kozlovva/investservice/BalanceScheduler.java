package ru.kozlovva.investservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kozlovva.investservice.service.impl.BalanceService;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceScheduler {

    private final static String brokerAccountId = "2022057604";
    private final BalanceService balanceService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedDelay = 1000 * 10)
    public void calculateBalance() {
        balanceService.calcBalance(brokerAccountId);
    }

}
