package work.hoodie.crypto.exchange.monitor.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;
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
    private WalletSummaryRetrieverService walletSummaryRetrieverService;
    @Autowired
    private WalletComparisonService walletComparisonService;

    private List<WalletSummary> oldWalletSummaries;
    private List<WalletSummary> newWalletSummaries;

    @PostConstruct
    public void retrieveNewWallets() throws IOException {
        newWalletSummaries = walletSummaryRetrieverService.getWalletSummaries();
    }

    public void sync() throws IOException {
        oldWalletSummaries = newWalletSummaries;
        retrieveNewWallets();
    }

    public WalletComparisonSummary getWalletSummary() throws IOException {
        List<WalletComparison> walletComparisons = new ArrayList<WalletComparison>();
        BigDecimal btcTotalChange = BigDecimal.ZERO;
        sync();
        for (WalletSummary walletSummary : newWalletSummaries) {
            WalletSummary matchingWalletCurrency = walletComparisonService.findMatchingWalletCurrency(oldWalletSummaries, walletSummary);
            WalletComparison comparison = walletComparisonService.compare(matchingWalletCurrency, walletSummary);
            btcTotalChange = btcTotalChange.add(comparison.getBtcValueGain());
            walletComparisons.add(comparison);
        }
        WalletComparisonSummary walletComparisonSummary = new WalletComparisonSummary();
        walletComparisonSummary.setWalletComparisons(walletComparisons);
        walletComparisonSummary.setBtcTotalChange(btcTotalChange);
        return walletComparisonSummary;
    }
}
