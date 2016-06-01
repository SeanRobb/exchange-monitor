package work.hoodie.crypto.exchange.monitor.service.wallet;

import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

import java.math.BigDecimal;
import java.util.List;

@Component
public class WalletComparisonService {

    public WalletComparison compare(WalletSummary oldWalletSummary, WalletSummary newWalletSummary) {
        String oldCurrency = oldWalletSummary.getCurrency();
        String newCurrency = newWalletSummary.getCurrency();
        BigDecimal newTotal = newWalletSummary.getTotal();
        BigDecimal oldTotal = oldWalletSummary.getTotal();
        BigDecimal newBtcValue = newWalletSummary.getBtcValue();
        BigDecimal oldBtcValue = oldWalletSummary.getBtcValue();

        WalletComparison walletComparison = new WalletComparison();
        if (oldCurrency.equals(newCurrency)) {
            walletComparison.setCurrency(oldWalletSummary.getCurrency());
            walletComparison.setOldSummary(oldWalletSummary);
            walletComparison.setNewSummary(newWalletSummary);
            walletComparison.setBalanceGain(newTotal.subtract(oldTotal));
            walletComparison.setBtcValueGain(newBtcValue.subtract(oldBtcValue));
        }
        return walletComparison;
    }

    public WalletSummary findMatchingWalletCurrency(List<WalletSummary> listToSearch, WalletSummary walletSummary) {
        for (WalletSummary walletCheck : listToSearch) {
            if (walletSummary.getCurrency().equalsIgnoreCase(walletCheck.getCurrency())) {
                return walletCheck;
            }
        }
        return new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(walletSummary.getCurrency())
                                .setAvailable(BigDecimal.ZERO)
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.ZERO);
    }
}
