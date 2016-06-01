package work.hoodie.crypto.exchange.monitor.service.balance;

import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderRetriever;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BalanceRetrieverService {

    @Autowired
    private PollingAccountService pollingAccountService;
    @Autowired
    private OpenOrderRetriever openOrderRetriever;
    @Autowired
    private OpenOrderService openOrderService;
    @Autowired
    private BalanceMarshaller balanceMarshaller;

    public List<Balance> getBalances() throws IOException {
        List<Wallet> wallets = pollingAccountService.getAccountInfo().getWallets();
        List<OpenOrder> openOrders = openOrderRetriever.getOpenOrders();
        ArrayList<Balance> balances = new ArrayList<Balance>();

        for (Wallet wallet : wallets) {
            OpenOrder totalOpenOrdersForCurrency = openOrderService.findTotalOpenOrdersForCurrency(openOrders, wallet.getCurrency());
            if (wallet.getBalance().doubleValue() > 0 || totalOpenOrdersForCurrency.getAmount().doubleValue() > 0) {
                Balance balance = balanceMarshaller.getBalance(wallet, totalOpenOrdersForCurrency);
                balances.add(balance);
            }
        }
        return balances;
    }
}
