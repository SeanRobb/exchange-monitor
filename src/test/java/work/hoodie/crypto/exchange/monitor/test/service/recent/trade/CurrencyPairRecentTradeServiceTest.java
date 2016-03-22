package work.hoodie.crypto.exchange.monitor.test.service.recent.trade;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.CurrencyPairRecentTradeService;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.QueryTimeRetrieveService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyPairRecentTradeServiceTest {

    @InjectMocks
    private CurrencyPairRecentTradeService currencyPairRecentTradeService;

    @Mock
    private QueryTimeRetrieveService queryTimeRetrieveService;
    @Mock
    private PollingTradeService pollingTradeService;

    @Test
    public void testGetHistory() throws Exception {
        Date queryTime = new Date();
        UserTrade expectedTrade = new UserTrade.Builder()
                .id("1")
                .timestamp(new DateTime(queryTime).plusMinutes(1).toDate())
                .build();
        UserTrade notExpectedTrade = new UserTrade.Builder()
                .id("2")
                .timestamp(new DateTime(queryTime).minusMinutes(1).toDate())
                .build();

        List<UserTrade> trades = new ArrayList<UserTrade>();
        trades.add(expectedTrade);
        trades.add(notExpectedTrade);

        ArrayList<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
        currencyPairs.add(CurrencyPair.BTC_AUD);
        currencyPairs.add(CurrencyPair.BTC_BRL);
        currencyPairs.add(CurrencyPair.BTC_CHF);

        when(queryTimeRetrieveService.getAndSyncQueryTime())
                .thenReturn(queryTime);
        when(pollingTradeService.getExchangeSymbols())
                .thenReturn(currencyPairs);
        when(pollingTradeService.getTradeHistory(any(DefaultTradeHistoryParamCurrencyPair.class)))
                .thenReturn(new UserTrades(trades, Trades.TradeSortType.SortByTimestamp));

        List<UserTrade> history = currencyPairRecentTradeService.getHistory();

        verify(queryTimeRetrieveService).getAndSyncQueryTime();
        verify(pollingTradeService).getExchangeSymbols();
        verify(pollingTradeService, times(currencyPairs.size())).getTradeHistory(any(DefaultTradeHistoryParamCurrencyPair.class));
        assertEquals(3, history.size());
        assertTrue(history.contains(expectedTrade));
        assertFalse(history.contains(notExpectedTrade));
    }
}
