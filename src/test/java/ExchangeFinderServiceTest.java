import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.ExchangeFinderService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeFinderServiceTest {
    @Test
    public void testFind_Poloniex() throws Exception {
        Class poloniex = new ExchangeFinderService().find("Poloniex");
        assertNotNull(poloniex);
        assertEquals(PoloniexExchange.class, poloniex);
    }

    @Test
    public void testFind_Bitfinex() throws Exception {
        Class bitfinex = new ExchangeFinderService().find("Bitfinex");
        assertNotNull(bitfinex);
        assertEquals(BitfinexExchange.class, bitfinex);

    }

    @Test
    public void testFind_Coinbase() throws Exception {
        Class coinbase = new ExchangeFinderService().find("Coinbase");
        assertNotNull(coinbase);
        assertEquals(CoinbaseExchange.class, coinbase);
    }
}
