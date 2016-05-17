package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WalletSummary {
    private List<WalletComparison> walletComparisons;
    private BigDecimal btcTotalChange;

    public String toString(){
        return "Total BTC Change: " + getBtcTotalChange().stripTrailingZeros().toPlainString() + " BTC";
    }
}
