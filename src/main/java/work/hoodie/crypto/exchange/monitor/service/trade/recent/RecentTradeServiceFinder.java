package work.hoodie.crypto.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.ExchangeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecentTradeServiceFinder {
    @Autowired
    private CurrencyPairRecentTradeService currencyPairRecentTradeService;
    @Autowired
    private SimpleRecentTradesService simpleRecentTradesService;

    public RecentTradesService find(ExchangeSpecification exchangeSpecification) {

        if (exchangeSpecification.getExchangeName().equalsIgnoreCase("Bitfinex")) {
            return currencyPairRecentTradeService;
        }

        return simpleRecentTradesService;
    }

}
