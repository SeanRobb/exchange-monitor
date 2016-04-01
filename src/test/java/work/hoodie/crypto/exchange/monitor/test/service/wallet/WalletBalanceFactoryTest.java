package work.hoodie.crypto.exchange.monitor.test.service.wallet;


import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletBalanceFactory;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletBalanceMarshaller;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletBalanceFactoryTest {
    @InjectMocks
    private WalletBalanceFactory classUnderTest;
    @Mock
    private PollingMarketDataService pollingMarketDataService;
    @Mock
    private WalletBalanceMarshaller walletBalanceMarshaller;
    @Mock
    private WalletBalance walletBalance;

    @Test
    public void testGetBalance() throws Exception {
        String currency = "test";
        BigDecimal balance = BigDecimal.valueOf(2);
        BigDecimal last = BigDecimal.valueOf(0.00000001);
        Wallet wallet = new Wallet(currency, balance);
        CurrencyPair currencyPair = new CurrencyPair(currency, WalletBalanceFactory.BTC);
        Ticker ticker = new Ticker.Builder()
                .currencyPair(currencyPair)
                .last(last)
                .build();

        when(pollingMarketDataService.getTicker(currencyPair))
                .thenReturn(ticker);
        when(walletBalanceMarshaller.convert(wallet, new OpenOrder(), ticker))
                .thenReturn(walletBalance);

        WalletBalance actual = classUnderTest.getBalance(wallet, new OpenOrder());

        assertNotNull(actual);
        verify(pollingMarketDataService).getTicker(eq(currencyPair));
        verify(walletBalanceMarshaller).convert(wallet, new OpenOrder(), ticker);
        assertEquals(walletBalance, actual);
    }
}
