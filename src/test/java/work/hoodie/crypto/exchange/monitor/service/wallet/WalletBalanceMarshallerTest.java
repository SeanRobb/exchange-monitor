package work.hoodie.crypto.exchange.monitor.service.wallet;


import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletBalanceFactory;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletBalanceMarshaller;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WalletBalanceMarshallerTest {
    @InjectMocks
    private WalletBalanceMarshaller classUnderTest;

    @Test
    public void testConvert() throws Exception {
        String currency = "test";
        BigDecimal openOrderValue = BigDecimal.ONE;
        BigDecimal balance = BigDecimal.valueOf(2);
        BigDecimal last = BigDecimal.valueOf(0.00000001);
        Wallet wallet = new Wallet(currency, balance);
        WalletBalance expected = new WalletBalance()
                .setCurrency(currency)
                .setLastPrice(last)
                .setBtcValue((balance.add(openOrderValue)).multiply(last))
                .setOnOrder(openOrderValue)
                .setAvailable(balance);
        CurrencyPair currencyPair = new CurrencyPair(currency, WalletBalanceFactory.BTC);
        Ticker ticker = new Ticker.Builder()
                .currencyPair(currencyPair)
                .last(last)
                .build();

        OpenOrder openOrder = new OpenOrder();
        openOrder.setCurrency(currency);
        openOrder.setAmount(openOrderValue);
        WalletBalance actual = classUnderTest.convert(wallet, openOrder, ticker);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
