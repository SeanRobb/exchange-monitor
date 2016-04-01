package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WalletSummary {
    private List<WalletComparison> walletComparisons;


    public BigDecimal getTotalChange(){
        return null;
    }
}
