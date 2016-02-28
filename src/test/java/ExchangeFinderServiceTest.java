import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.ExchangeFinderService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeFinderServiceTest {
    @InjectMocks
    private ExchangeFinderService exchangeFinderService;

    @Before
    public void init(){
        exchangeFinderService.init();
    }

    @Test
    public void testFind_Poloniex() throws Exception {
        Class poloniex = exchangeFinderService.find("Poloniex");
        assertNotNull(poloniex);
        assertEquals(PoloniexExchange.class, poloniex);
    }

    @Test
    public void testFind_Bitfinex() throws Exception {
        Class bitfinex = exchangeFinderService.find("Bitfinex");
        assertNotNull(bitfinex);
        assertEquals(BitfinexExchange.class, bitfinex);

    }

}
