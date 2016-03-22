package work.hoodie.crypto.exchange.monitor.test.service;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.TradeConverter;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TradeConverterTest {
    @InjectMocks
    private TradeConverter tradeConverter;

    @Test
    public void getTotal_CalculatesTotalForSingleCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        BigDecimal total = tradeConverter.getTotal(userTrade);
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

        BigDecimal total = tradeConverter.getTotal(userTrade);
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

        BigDecimal total = tradeConverter.getTotal(userTrade);
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


        BigDecimal coinSent = tradeConverter.getCoinSent(userTrade);

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


        BigDecimal actual = tradeConverter.getCoinSent(userTrade);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }


    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedBID() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("BTC")
                .price(BigDecimal.valueOf(10))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(CurrencyPair.LTC_BTC)
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        BigDecimal coinReceived = tradeConverter.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(0.9, coinReceived.doubleValue(), 0.0001);
    }

    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedBID2() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("BTC")
                .price(BigDecimal.valueOf(0.00022653))
                .feeAmount(BigDecimal.valueOf(0.000014175965862852750000))
                .currencyPair(new CurrencyPair("MAID", "BTC"))
                .tradableAmount(BigDecimal.valueOf(41.71917145))
                .build();

        BigDecimal coinReceived = tradeConverter.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(41.65659269, coinReceived.doubleValue(), 0.0001);
    }


    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedBID3() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("MAID")
                .price(BigDecimal.valueOf(0.00022653))
                .feeAmount(BigDecimal.valueOf(0.06257876))
                .currencyPair(new CurrencyPair("MAID", "BTC"))
                .tradableAmount(BigDecimal.valueOf(41.71917145))
                .build();

        BigDecimal coinReceived = tradeConverter.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(41.65659269, coinReceived.doubleValue(), 0.0001);
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

        BigDecimal coinReceived = tradeConverter.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(90, coinReceived.doubleValue(), 0.0001);
    }

    @Test
    public void getCoinReceived_AmountCoinReceivedIsReturnedASK2() {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .feeCurrency("LTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(CurrencyPair.LTC_BTC)
                .tradableAmount(BigDecimal.valueOf(100))
                .build();

        BigDecimal coinReceived = tradeConverter.getCoinReceived(userTrade);

        assertNotNull(coinReceived);
        assertEquals(99, coinReceived.doubleValue(), 0.0001);
    }

    @Test
    public void getCoinReceivedName_BID() {
        CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("LTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(currencyPair)
                .tradableAmount(BigDecimal.valueOf(100))
                .build();

        String coinRecievedName = tradeConverter.getCoinReceivedName(userTrade);

        assertEquals(currencyPair.baseSymbol, coinRecievedName);
    }

    @Test
    public void getCoinReceivedName_ASK() {
        CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .feeCurrency("LTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(currencyPair)
                .tradableAmount(BigDecimal.valueOf(100))
                .build();

        String coinRecievedName = tradeConverter.getCoinReceivedName(userTrade);


        assertEquals(currencyPair.counterSymbol, coinRecievedName);
    }

    @Test
    public void getCoinSentName_BID() {
        CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("LTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(currencyPair)
                .tradableAmount(BigDecimal.valueOf(100))
                .build();

        String coinRecievedName = tradeConverter.getCoinSentName(userTrade);
        assertEquals(currencyPair.counterSymbol, coinRecievedName);
    }

    @Test
    public void getCoinSentName_ASK() {
        CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .feeCurrency("LTC")
                .price(BigDecimal.valueOf(100))
                .feeAmount(BigDecimal.valueOf(1))
                .currencyPair(currencyPair)
                .tradableAmount(BigDecimal.valueOf(100))
                .build();

        String coinRecievedName = tradeConverter.getCoinSentName(userTrade);

        assertEquals(currencyPair.baseSymbol, coinRecievedName);
    }

}
