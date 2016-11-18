package work.hoodie.exchange.monitor.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class WalletSummary {

    private Balance balance;
    private BigDecimal btcValue;
    private BigDecimal lastPrice;

    public String getCurrency(){
        return balance.getCurrency();
    }

    public BigDecimal getAvailable(){
        return balance.getAvailable();
    }

    public BigDecimal getOnOrder(){
        return balance.getOnOrder();
    }

    public BigDecimal getTotal(){
        return getAvailable().add(getOnOrder());
    }
}
