import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.bittrex.v1.BittrexExchange;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.service.trade.recent.CurrencyPairRecentTradeService;
import work.hoodie.exchange.monitor.service.trade.recent.RecentTradeServiceFinder;
import work.hoodie.exchange.monitor.service.trade.recent.RecentTradesService;
import work.hoodie.exchange.monitor.service.trade.recent.SimpleRecentTradesService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RecentTradeServiceFinderTest {
    @InjectMocks
    private RecentTradeServiceFinder recentTradesServiceFinder;
    @Mock
    private CurrencyPairRecentTradeService currencyPairRecentTradeService;
    @Mock
    private SimpleRecentTradesService simpleRecentTradesService;

    @Test
    public void testFind_Poloniex() throws Exception {
        ExchangeSpecification exchangeSpecification = ExchangeFactory.INSTANCE
                .createExchange(new ExchangeSpecification(PoloniexExchange.class))
                .getExchangeSpecification();
        RecentTradesService recentTradesService = recentTradesServiceFinder.find(exchangeSpecification);
        assertNotNull(recentTradesService);
        Assert.assertEquals(simpleRecentTradesService,recentTradesService);
    }

    @Test
    public void testFind_BitFinex() throws Exception {
        ExchangeSpecification exchangeSpecification =  ExchangeFactory.INSTANCE
                .createExchange(new ExchangeSpecification(BitfinexExchange.class))
                .getExchangeSpecification();
        RecentTradesService recentTradesService = recentTradesServiceFinder.find(exchangeSpecification);
        assertNotNull(recentTradesService);
        Assert.assertEquals(currencyPairRecentTradeService,recentTradesService);
    }
    @Test
    public void testFind_Bittrex() throws Exception {
        ExchangeSpecification exchangeSpecification =  ExchangeFactory.INSTANCE
                .createExchange(new ExchangeSpecification(BittrexExchange.class))
                .getExchangeSpecification();
        RecentTradesService recentTradesService = recentTradesServiceFinder.find(exchangeSpecification);
        assertNotNull(recentTradesService);
        Assert.assertEquals(currencyPairRecentTradeService,recentTradesService);
    }
}
