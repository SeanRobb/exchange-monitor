package work.hoodie.crypto.exchange.monitor.test.service.trade.open;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class OpenOrderServiceTest {
    @InjectMocks
    private OpenOrderService classUnderTest;


    @Test
    public void testFindTotalOpenOrdersForCurrency_OneCurrency() throws Exception {
        List<OpenOrder> orderList = new ArrayList<OpenOrder>();
        String currency1 = "Currency1";
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));

        OpenOrder totalOpenOrdersForCurrency = classUnderTest.findTotalOpenOrdersForCurrency(orderList, currency1);

        assertNotNull(totalOpenOrdersForCurrency);
        assertEquals(currency1, totalOpenOrdersForCurrency.getCurrency());
        assertEquals(BigDecimal.valueOf(3), totalOpenOrdersForCurrency.getAmount());
    }

    @Test
    public void testFindTotalOpenOrdersForCurrency_TwoCurrency() throws Exception {
        List<OpenOrder> orderList = new ArrayList<OpenOrder>();
        String currency1 = "Currency1";
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency("Other Currency1").setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency("Other Currency2").setAmount(BigDecimal.ONE));
        orderList.add(new OpenOrder().setCurrency(currency1).setAmount(BigDecimal.ONE));

        OpenOrder totalOpenOrdersForCurrency = classUnderTest.findTotalOpenOrdersForCurrency(orderList, currency1);

        assertNotNull(totalOpenOrdersForCurrency);
        assertEquals(currency1, totalOpenOrdersForCurrency.getCurrency());
        assertEquals(BigDecimal.valueOf(3), totalOpenOrdersForCurrency.getAmount());
    }
}
