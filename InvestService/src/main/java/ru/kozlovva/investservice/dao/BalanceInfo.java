package ru.kozlovva.investservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BalanceInfo {
    private ObjectId id;
    private BigDecimal freeMoney;
    private BigDecimal activeMoney;
    private BigDecimal total;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime date;

    public BalanceInfo(BigDecimal freeMoney, BigDecimal activeMoney, BigDecimal total) {
        this.freeMoney = freeMoney.setScale(2, RoundingMode.HALF_DOWN);
        this.activeMoney = activeMoney.setScale(2, RoundingMode.HALF_DOWN);;
        this.total = total.setScale(2, RoundingMode.HALF_DOWN);;
        this.id = ObjectId.get();
        this.date = LocalDateTime.now();
    }

    public static BalanceInfo create(BigDecimal freeMoney, BigDecimal activeMoney, BigDecimal total) {
        return new BalanceInfo(freeMoney, activeMoney, total);
    }
}
