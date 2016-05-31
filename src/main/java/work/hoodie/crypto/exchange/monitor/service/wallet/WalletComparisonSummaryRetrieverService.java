package work.hoodie.crypto.exchange.monitor.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletComparisonSummaryRetrieverService {

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

    public WalletComparisonSummary getWalletSummary() throws IOException {
        List<WalletComparison> walletComparisons = new ArrayList<WalletComparison>();
        BigDecimal btcTotalChange = BigDecimal.ZERO;
        sync();
        for (WalletBalance walletBalance : newWalletBalances) {
            WalletBalance matchingWalletCurrency = walletComparisonService.findMatchingWalletCurrency(oldWalletBalances, walletBalance);
            WalletComparison comparison = walletComparisonService.compare(matchingWalletCurrency, walletBalance);
            btcTotalChange = btcTotalChange.add(comparison.getBtcValueGain());
            walletComparisons.add(comparison);
        }
        WalletComparisonSummary walletComparisonSummary = new WalletComparisonSummary();
        walletComparisonSummary.setWalletComparisons(walletComparisons);
        walletComparisonSummary.setBtcTotalChange(btcTotalChange);
        return walletComparisonSummary;
    }
}
