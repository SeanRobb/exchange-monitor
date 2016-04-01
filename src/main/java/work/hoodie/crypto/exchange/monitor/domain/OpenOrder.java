package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OpenOrder {
    private String currency;
    private BigDecimal amount;
}
