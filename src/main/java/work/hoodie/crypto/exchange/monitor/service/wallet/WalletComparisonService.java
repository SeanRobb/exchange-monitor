package work.hoodie.crypto.exchange.monitor.service.wallet;

import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;

import java.math.BigDecimal;
import java.util.List;

@Component
public class WalletComparisonService {

    public WalletComparison compare(WalletBalance oldWalletBalance, WalletBalance newWalletBalance) {
        String oldCurrency = oldWalletBalance.getCurrency();
        String newCurrency = newWalletBalance.getCurrency();
        BigDecimal newTotal = newWalletBalance.getTotal();
        BigDecimal oldTotal = oldWalletBalance.getTotal();
        BigDecimal newBtcValue = newWalletBalance.getBtcValue();
        BigDecimal oldBtcValue = oldWalletBalance.getBtcValue();

        WalletComparison walletComparison = new WalletComparison();
        if (oldCurrency.equals(newCurrency)) {
            walletComparison.setCurrency(oldWalletBalance.getCurrency());
            walletComparison.setOldBalance(oldWalletBalance);
            walletComparison.setNewBalance(newWalletBalance);
            walletComparison.setBalanceGain(newTotal.subtract(oldTotal));
            walletComparison.setBtcValueGain(newBtcValue.subtract(oldBtcValue));
        }
        return walletComparison;
    }

    public WalletBalance findMatchingWalletCurrency(List<WalletBalance> listToSearch, WalletBalance walletBalance) {
        for (WalletBalance walletCheck : listToSearch) {
            if (walletBalance.getCurrency().equalsIgnoreCase(walletCheck.getCurrency())) {
                return walletCheck;
            }
        }
        return new WalletBalance()
                .setCurrency(walletBalance.getCurrency())
                .setBtcValue(BigDecimal.ZERO)
                .setAvailable(BigDecimal.ZERO);
    }
}
