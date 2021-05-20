package ru.kozlovva.investservice.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kozlovva.investservice.dao.BalanceInfo;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = "/api/v1/balance")
    public ResponseEntity<BalanceInfo> getBalance() {
        return ResponseEntity.ok(profileService.getBalance());
    }

}
