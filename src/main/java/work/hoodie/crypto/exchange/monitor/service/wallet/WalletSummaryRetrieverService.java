package work.hoodie.crypto.exchange.monitor.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletSummaryRetrieverService {

    @Autowired
    private WalletRetrieverService walletRetrieverService;
    @Autowired
    private WalletComparisonService walletComparisonService;

    private List<WalletBalance> oldWalletBalances;
    private List<WalletBalance> newWalletBalances;

    @PostConstruct
    public void retrieveNewWallets() throws IOException {
        newWalletBalances = walletRetrieverService.getWalletBalances();
    }

    public void sync() throws IOException {
        oldWalletBalances = newWalletBalances;
        retrieveNewWallets();
    }

    public WalletSummary getWalletSummary() throws IOException {
        List<WalletComparison> walletComparisons = new ArrayList<WalletComparison>();
        sync();
        for (WalletBalance walletBalance : newWalletBalances) {
            WalletBalance matchingWalletCurrency = walletComparisonService.findMatchingWalletCurrency(oldWalletBalances, walletBalance);
            WalletComparison comparison = walletComparisonService.compare(matchingWalletCurrency, walletBalance);
            walletComparisons.add(comparison);
        }
        WalletSummary walletSummary = new WalletSummary();
        walletSummary.setWalletComparisons(walletComparisons);
        return walletSummary;
    }
}
