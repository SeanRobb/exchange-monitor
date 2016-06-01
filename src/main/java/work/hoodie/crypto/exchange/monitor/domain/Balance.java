package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@Data
@Accessors(chain = true)
public class Balance {
    private String currency;
    private BigDecimal available;
    private BigDecimal onOrder;

    public BigDecimal getTotal() {
        return BigDecimal.ZERO.add(available).add(onOrder);
    }
}
