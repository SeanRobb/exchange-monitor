import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.OpenOrder;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.service.balance.BalanceMarshaller;
import work.hoodie.exchange.monitor.service.wallet.WalletSummaryFactory;
import work.hoodie.exchange.monitor.service.wallet.WalletSummaryMarshaller;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WalletSummaryMarshallerTest {
    @InjectMocks
    private WalletSummaryMarshaller classUnderTest;

    @Mock
    private BalanceMarshaller balanceMarshaller;

    @Test
    public void testConvert() throws Exception {
        String currency = "test";
        BigDecimal openOrderValue = BigDecimal.ONE;
        BigDecimal balance = BigDecimal.valueOf(2);
        BigDecimal last = BigDecimal.valueOf(0.00000001);
        Balance expectedBalance = new Balance()
                .setCurrency(currency)
                .setOnOrder(openOrderValue)
                .setAvailable(balance);
        WalletSummary expected = new WalletSummary()
                .setBalance(expectedBalance)
                .setLastPrice(last)
                .setBtcValue((balance.add(openOrderValue)).multiply(last));
        CurrencyPair currencyPair = new CurrencyPair(currency, WalletSummaryFactory.BTC);
        Ticker ticker = new Ticker.Builder()
                .currencyPair(currencyPair)
                .last(last)
                .build();

        OpenOrder openOrder = new OpenOrder();
        openOrder.setCurrency(currency);
        openOrder.setAmount(openOrderValue);


        WalletSummary actual = classUnderTest.convert(ticker, expectedBalance);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
