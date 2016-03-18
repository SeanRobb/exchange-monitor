import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.GreatNumberCalculator;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ryantodd on 3/8/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GreatNumberCalculatorTest {
    @InjectMocks
    private GreatNumberCalculator greatNumberCalculator;

    @Test
    public void getTotal_CalculatesTotalForSingleCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(400), total);
    }

    @Test
    public void getTotal_CalculatesTotalForDeuceCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(2))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(800), total);
    }

    @Test
    public void getTotal_CalculatesTotalForHalfCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(.5))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(200).doubleValue(), total.doubleValue(), 0.001);
    }

    @Test
    public void getCoinSent_ActualAmountOfCoinsForBID() {
        BigDecimal expected = BigDecimal.valueOf(1);
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(expected)
                .build();


        BigDecimal coinSent = greatNumberCalculator.getCoinSent(userTrade);

        assertNotNull(coinSent);
        assertEquals(expected, coinSent);
    }


    @Test
    public void getCoinSent_ActualAmountOfCoinsForAsk() {
        BigDecimal price = BigDecimal.valueOf(400);
        BigDecimal tradableAmount = BigDecimal.valueOf(2);
        BigDecimal expected = price.multiply(tradableAmount);
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .price(price)
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(tradableAmount)
                .build();


        BigDecimal actual = greatNumberCalculator.getCoinSent(userTrade);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }


    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedBID() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("BTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(CurrencyPair.LTC_BTC)
                .tradableAmount(BigDecimal.valueOf(10))
                .build();

        BigDecimal coinReceived = greatNumberCalculator.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(9.99, coinReceived.doubleValue(), 0.0001);
    }


    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedASK() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .feeCurrency("BTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(10))
                .currencyPair(CurrencyPair.LTC_BTC)
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        BigDecimal coinReceived = greatNumberCalculator.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(90, coinReceived.doubleValue(), 0.0001);
    }
}
