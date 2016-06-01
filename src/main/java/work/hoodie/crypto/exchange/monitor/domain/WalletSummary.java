package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class WalletSummary {
//    private String currency;
//    private BigDecimal available;
//    private BigDecimal onOrder;

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
