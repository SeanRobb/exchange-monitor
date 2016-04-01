package work.hoodie.crypto.exchange.monitor.service.wallet;

import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderRetriever;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletRetrieverService {
    @Autowired
    private PollingAccountService pollingAccountService;
    @Autowired
    private WalletBalanceFactory walletBalanceFactory;
    @Autowired
    private OpenOrderRetriever openOrderRetriever;
    @Autowired
    private OpenOrderService openOrderService;

    public List<WalletBalance> getWalletBalances() throws IOException {
        List<WalletBalance> walletBalances = new ArrayList<WalletBalance>();
        List<Wallet> wallets = pollingAccountService.getAccountInfo().getWallets();
        List<OpenOrder> openOrders = openOrderRetriever.getOpenOrders();
        for (Wallet wallet : wallets) {
            OpenOrder totalOpenOrdersForCurrency = openOrderService.findTotalOpenOrdersForCurrency(openOrders, wallet.getCurrency());
            if (wallet.getBalance().doubleValue() > 0 || totalOpenOrdersForCurrency.getAmount().doubleValue() > 0) {
                WalletBalance balance = walletBalanceFactory.getBalance(wallet, totalOpenOrdersForCurrency);
                walletBalances.add(balance);
            }
        }
        return walletBalances;
    }
}
