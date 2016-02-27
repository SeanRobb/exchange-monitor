package work.hoodie.crypto.exchange.monitor.service;


import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.poloniex.PoloniexExchange;

import java.util.ArrayList;
import java.util.List;

public class ExchangeFinderService {

    private final List<Exchange> exchanges = new ArrayList<Exchange>();

    public ExchangeFinderService() {
        exchanges.add(ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName()));
        exchanges.add(ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName()));
        exchanges.add(ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName()));
    }


    public Class find(String exchangeToFind) {
        for (Exchange exchange : exchanges) {
            String exchangeName = exchange.getExchangeSpecification().getExchangeName();
            if (exchangeName.equalsIgnoreCase(exchangeToFind)) {
                return exchange.getClass();
            }
        }
        return null;
    }

}
