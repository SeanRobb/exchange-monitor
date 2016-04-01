package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class WalletBalance {
    private String currency;
    private BigDecimal available;
    private BigDecimal onOrder;
    private BigDecimal btcValue;

    public BigDecimal getTotal(){
        return available.add(onOrder);
    }
}
