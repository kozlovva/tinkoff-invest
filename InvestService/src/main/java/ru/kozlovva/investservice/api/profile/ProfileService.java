package ru.kozlovva.investservice.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kozlovva.investservice.dao.BalanceInfo;
import ru.kozlovva.investservice.dao.BalanceRepository;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final BalanceRepository balanceRepository;

    public BalanceInfo getBalance() {
        return balanceRepository.findFirstByOrderByDateDesc();
    }

}
