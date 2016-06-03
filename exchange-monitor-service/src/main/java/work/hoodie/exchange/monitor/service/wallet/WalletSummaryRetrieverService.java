package work.hoodie.exchange.monitor.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.service.balance.BalanceRetrieverService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletSummaryRetrieverService {
    @Autowired
    private WalletSummaryFactory walletSummaryFactory;
    @Autowired
    private BalanceRetrieverService balanceRetrieverService;

    public List<WalletSummary> getWalletSummaries() throws IOException {
        List<WalletSummary> walletSummaries = new ArrayList<WalletSummary>();
        List<Balance> balances = balanceRetrieverService.getBalances();

        for (Balance balance : balances) {
            if (balance.getAvailable().doubleValue() > 0 || balance.getOnOrder().doubleValue() > 0) {
                WalletSummary walletSummary = walletSummaryFactory.getSummary(balance);
                walletSummaries.add(walletSummary);
            }
        }
        return walletSummaries;
    }
}
