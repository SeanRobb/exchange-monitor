package work.hoodie.crypto.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.TimeRetrieveService;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.SimpleRecentTradesService;

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
    @Mock
    private TimeRetrieveService timeRetrieveService;

    @Test
    public void testGetHistory() throws Exception {
        ArrayList<UserTrade> trades = new ArrayList<UserTrade>();
        trades.add(new UserTrade.Builder().build());

        when(pollingTradeService.getTradeHistory(any(DefaultTradeHistoryParamsTimeSpan.class)))
                .thenReturn(new UserTrades(trades, Trades.TradeSortType.SortByTimestamp));
        when(timeRetrieveService.getAndSyncQueryTime())
                .thenReturn(new Date());

        List<UserTrade> history = simpleRecentTradesService.getHistory();

        assertNotNull(history);
        verify(pollingTradeService).getTradeHistory(any(DefaultTradeHistoryParamsTimeSpan.class));
        verify(timeRetrieveService).getAndSyncQueryTime();
        assertEquals(trades, history);

    }
}
