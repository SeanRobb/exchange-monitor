package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletComparison {
    private String currency;
    private WalletSummary oldSummary;
    private WalletSummary newSummary;
    private BigDecimal balanceGain;
    private BigDecimal btcValueGain;

    public BigDecimal getBalance() {
        return newSummary.getTotal();
    }

    public BigDecimal getBtcValue() {
        return newSummary.getBtcValue();
    }

    public BigDecimal getNewLastPrice() {
        return newSummary.getLastPrice();
    }

    public BigDecimal getOldLastPrice() {
        return oldSummary.getLastPrice();
    }

    public String toString() {
        return "\tCurrency: " + getCurrency() +
                "\n\t\tTicker Was: " + getNiceString(getOldLastPrice()) + " Is: " + getNiceString(getNewLastPrice()) +
                "\n\t\t\tBalance:\t\t\t\t" + getNiceString(getBalance()) +
                "\n\t\t\tBTC Value:\t\t\t" + getNiceString(getBtcValue()) +
                "\n\t\t\tBalance Gain:\t\t" + getNiceString(getBalanceGain()) +
                "\n\t\t\tBTC Value Gain:\t" + getNiceString(getBtcValueGain()) + " BTC" + "\n";
    }

    private String getNiceString(BigDecimal bigDecimal) {
        return bigDecimal.stripTrailingZeros().toPlainString();
    }
}
