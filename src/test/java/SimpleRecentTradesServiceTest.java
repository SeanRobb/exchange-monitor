import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.service.SimpleRecentTradesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleRecentTradesServiceTest {
    @InjectMocks
    private SimpleRecentTradesService simpleRecentTradesService;
    @Mock
    private PollingTradeService pollingTradeService;

    @Before
    public void init() {
        ReflectionTestUtils.setField(simpleRecentTradesService, "startTime", new Date());
    }

    @Test
    public void testGetHistory() throws Exception {
        ArrayList<UserTrade> trades = new ArrayList<UserTrade>();
        trades.add(new UserTrade.Builder().build());

        when(pollingTradeService.getTradeHistory(any(DefaultTradeHistoryParamsTimeSpan.class)))
                .thenReturn(new UserTrades(trades, Trades.TradeSortType.SortByTimestamp));

        List<UserTrade> history = simpleRecentTradesService.getHistory();

        assertNotNull(history);
        verify(pollingTradeService).getTradeHistory(any(DefaultTradeHistoryParamsTimeSpan.class));
        assertEquals(trades, history);

    }
}
