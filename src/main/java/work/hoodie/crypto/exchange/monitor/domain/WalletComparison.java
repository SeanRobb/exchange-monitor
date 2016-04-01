package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletComparison {
    private String currency;
    private WalletBalance oldBalance;
    private WalletBalance newBalance;
    private BigDecimal balanceGain;
    private BigDecimal btcValueGain;

    public BigDecimal getBalance() {
        return newBalance.getTotal();
    }

    public BigDecimal getBtcValue() {
        return newBalance.getBtcValue();
    }
}
