package work.hoodie.crypto.exchange.monitor.service.wallet;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;

import java.math.BigDecimal;

@Component
public class WalletBalanceFactory {

    public static final String BTC = "BTC";
    @Autowired
    private PollingMarketDataService pollingMarketDataService;
    @Autowired
    private WalletBalanceMarshaller walletBalanceMarshaller;

    public WalletBalance getBalance(Wallet wallet, OpenOrder openOrder) {
        Ticker ticker = new Ticker.Builder().last(BigDecimal.ZERO).build();
        try {
            if (wallet.getCurrency().equalsIgnoreCase(BTC)) {
                ticker = new Ticker.Builder().last(BigDecimal.ONE).build();
            } else {
                CurrencyPair currencyPair = new CurrencyPair(wallet.getCurrency(), BTC);
                ticker = pollingMarketDataService.getTicker(currencyPair);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return walletBalanceMarshaller.convert(wallet, openOrder, ticker);
    }
}
