import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.service.wallet.WalletSummaryFactory;
import work.hoodie.exchange.monitor.service.wallet.WalletSummaryMarshaller;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletSummaryFactoryTest {
    @InjectMocks
    private WalletSummaryFactory classUnderTest;
    @Mock
    private PollingMarketDataService pollingMarketDataService;
    @Mock
    private WalletSummaryMarshaller walletSummaryMarshaller;
    @Mock
    private WalletSummary walletSummary;

    @Test
    public void testGetBalance() throws Exception {
        String currency = "test";
        BigDecimal balanceValue = BigDecimal.valueOf(2);
        BigDecimal last = BigDecimal.valueOf(0.00000001);
        Balance balance = new Balance().setCurrency(currency).setAvailable(balanceValue);
        CurrencyPair currencyPair = new CurrencyPair(currency, WalletSummaryFactory.BTC);
        Ticker ticker = new Ticker.Builder()
                .currencyPair(currencyPair)
                .last(last)
                .build();

        when(pollingMarketDataService.getTicker(currencyPair))
                .thenReturn(ticker);
        when(walletSummaryMarshaller.convert(ticker, balance))
                .thenReturn(walletSummary);

        WalletSummary actual = classUnderTest.getSummary(balance);

        assertNotNull(actual);
        verify(pollingMarketDataService).getTicker(eq(currencyPair));
        verify(walletSummaryMarshaller).convert(ticker,balance);
        assertEquals(walletSummary, actual);
    }
}
