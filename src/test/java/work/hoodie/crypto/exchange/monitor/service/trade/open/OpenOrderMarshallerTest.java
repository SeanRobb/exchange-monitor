package work.hoodie.crypto.exchange.monitor.service.trade.open;


import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class OpenOrderMarshallerTest {
    @InjectMocks
    private OpenOrderMarshaller openOrderMarshaller;

    @Test
    public void testConvert_Bid() throws Exception {
        Order.OrderType orderType = Order.OrderType.BID;
        CurrencyPair currencyPair = CurrencyPair.BTC_USD;

        LimitOrder limitOrder = new LimitOrder
                .Builder(orderType, currencyPair)
                .tradableAmount(BigDecimal.TEN)
                .limitPrice(BigDecimal.valueOf(2))
                .build();

        OpenOrder actual = openOrderMarshaller.convert(limitOrder);

        assertNotNull(actual);
        assertNotNull(actual.getCurrency());
        assertNotNull(actual.getAmount());
        assertEquals(currencyPair.counterSymbol, actual.getCurrency());
        assertEquals(BigDecimal.valueOf(20), actual.getAmount());
    }

    @Test
    public void testConvert_Ask() throws Exception {
        Order.OrderType orderType = Order.OrderType.ASK;
        CurrencyPair currencyPair = CurrencyPair.BTC_USD;

        LimitOrder limitOrder = new LimitOrder
                .Builder(orderType, currencyPair)
                .tradableAmount(BigDecimal.TEN)
                .limitPrice(BigDecimal.valueOf(2))
                .build();

        OpenOrder actual = openOrderMarshaller.convert(limitOrder);

        assertNotNull(actual);
        assertNotNull(actual.getCurrency());
        assertNotNull(actual.getAmount());
        assertEquals(currencyPair.baseSymbol, actual.getCurrency());
        assertEquals(BigDecimal.valueOf(10), actual.getAmount());
    }
}
