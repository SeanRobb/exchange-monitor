package work.hoodie.exchange.monitor.service.wallet;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletSummary;

import java.math.BigDecimal;

@Component
public class WalletSummaryFactory {

    public static final String BTC = "BTC";
    @Autowired
    private PollingMarketDataService pollingMarketDataService;
    @Autowired
    private WalletSummaryMarshaller walletSummaryMarshaller;

    public WalletSummary getSummary(Balance balance) {
        Ticker ticker = new Ticker.Builder().last(BigDecimal.ZERO).build();
        try {
            if (balance.getCurrency().equalsIgnoreCase(BTC)) {
                ticker = new Ticker.Builder().last(BigDecimal.ONE).build();
            } else {
                CurrencyPair currencyPair = new CurrencyPair(balance.getCurrency(), BTC);
                ticker = pollingMarketDataService.getTicker(currencyPair);
            }
        } catch (Exception e) {

        }
        return walletSummaryMarshaller.convert(ticker, balance);
    }
}
