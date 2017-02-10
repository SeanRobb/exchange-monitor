import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.RecentUserTrade;
import work.hoodie.exchange.monitor.service.trade.recent.RecentUserTradeMarshaller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class RecentUserTradeMarshallerTest {
    @InjectMocks
    private RecentUserTradeMarshaller recentUserTradeMarshaller;

    @Test
    public void convertSingle() {
        Order.OrderType orderType = Order.OrderType.BID;
        String id = "XEM";
        CurrencyPair currencyPair = CurrencyPair.BTC_USD;
        BigDecimal price = BigDecimal.ONE;
        Date timeStamp = new Date();

        UserTrade userTrade = new UserTrade
                .Builder()
                .type(orderType)
                .id(id)
                .currencyPair(currencyPair)
                .price(price)
                .timestamp(timeStamp)
                .build();

        RecentUserTrade trade = recentUserTradeMarshaller.convert(userTrade);


        assertNotNull(trade);
        assertEquals(orderType, trade.getType());
        assertEquals(id, trade.getTradeId());
        assertEquals(currencyPair, trade.getCurrencyPair());
        assertEquals(price, trade.getPrice());
        assertEquals(timeStamp, trade.getTimestamp());

    }

    @Test
    public void convertList() {
        Order.OrderType orderType1 = Order.OrderType.BID;
        String id1 = "XEM";
        CurrencyPair currencyPair1 = CurrencyPair.BTC_USD;
        BigDecimal price1 = BigDecimal.ONE;
        Date timeStamp1 = new Date();
        Order.OrderType orderType2 = Order.OrderType.BID;
        String id2 = "XMR";
        CurrencyPair currencyPair2 = CurrencyPair.BTC_USD;
        BigDecimal price2 = BigDecimal.TEN;
        Date timeStamp2 = new Date();

        UserTrade userTrade1 = new UserTrade
                .Builder()
                .type(orderType2)
                .id(id2)
                .currencyPair(currencyPair2)
                .price(price2)
                .timestamp(timeStamp2)
                .build();
        UserTrade userTrade2 = new UserTrade
                .Builder()
                .type(orderType2)
                .id(id2)
                .currencyPair(currencyPair2)
                .price(price2)
                .timestamp(timeStamp2)
                .build();

        List<UserTrade> userTrades = asList(userTrade1, userTrade2);

        List<RecentUserTrade> convert = recentUserTradeMarshaller.convert(userTrades);


        Assert.assertNotNull(convert);
        assertFalse(convert.isEmpty());

        convert.stream()
                .forEach(recentUserTrade -> {
                    assertNotNull(convert);
                    assertEquals(orderType2, recentUserTrade.getType());
                    assertEquals(id2, recentUserTrade.getTradeId());
                    assertEquals(currencyPair2, recentUserTrade.getCurrencyPair());
                    assertEquals(price2, recentUserTrade.getPrice());
                    assertEquals(timeStamp2, recentUserTrade.getTimestamp());

                });


    }

}
