import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.OpenOrder;
import work.hoodie.exchange.monitor.service.trade.open.OpenOrderMarshaller;
import work.hoodie.exchange.monitor.service.trade.open.OpenOrderRetriever;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpenOrderRetrieverTest {

    @InjectMocks
    private OpenOrderRetriever classUnderTest;

    @Mock
    private PollingTradeService pollingTradeService;
    @Mock
    private OpenOrderMarshaller openOrderMarshaller;
    @Mock
    private LimitOrder limitOrder;
    @Mock
    private OpenOrder openOrder;

    @Test
    public void testGetOpenOrders_NoOrders() throws Exception {

        List<LimitOrder> openOrders = new ArrayList<LimitOrder>();

        when(pollingTradeService.getOpenOrders())
                .thenReturn(new OpenOrders(openOrders));

        List<OpenOrder> actual = classUnderTest.getOpenOrders();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetOpenOrders() throws Exception {

        List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
        openOrders.add(limitOrder);

        when(pollingTradeService.getOpenOrders())
                .thenReturn(new OpenOrders(openOrders));
        when(openOrderMarshaller.convert(limitOrder))
                .thenReturn(openOrder);

        List<OpenOrder> actual = classUnderTest.getOpenOrders();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        verify(pollingTradeService).getOpenOrders();
        verify(openOrderMarshaller).convert(limitOrder);
        assertTrue(actual.contains(openOrder));
        assertEquals(1,openOrders.size());
    }
}
