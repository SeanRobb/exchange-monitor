import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.RecentUserTrade;
import work.hoodie.exchange.monitor.service.trade.recent.RecentUserTradeMarshaller;

import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by ryantodd on 2/3/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RecentUserTradeMarshallerTest {
    @InjectMocks
    private RecentUserTradeMarshaller recentUserTradeMarshaller;

    @Test
    public void convertSingle(){
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
        assertEquals(orderType,trade.getType());
        assertEquals(id, trade.getTradeId());
        assertEquals(currencyPair,trade.getCurrencyPair());
        assertEquals(price,trade.getPrice());
        assertEquals(timeStamp,trade.getTimestamp());

    }
    @Test
    public void convertList(){
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
        RecentUserTrade trade1 = recentUserTradeMarshaller.convert(userTrade1);
        RecentUserTrade trade2 = recentUserTradeMarshaller.convert(userTrade2);





        assertNotNull(trade1);
        assertNotNull(trade2);
        assertEquals(orderType2,trade1.getType());
        assertEquals(id2, trade1.getTradeId());
        assertEquals(currencyPair2,trade1.getCurrencyPair());
        assertEquals(price2,trade1.getPrice());
        assertEquals(timeStamp2,trade1.getTimestamp());

    }

}
