package work.hoodie.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CurrencyPairRecentTradeService implements RecentTradesService {
    @Autowired
    private PollingTradeService pollingTradeService;
    @Autowired
    private TimeRetrieveService timeRetrieveService;

    public List<UserTrade> getHistory() {
        List<UserTrade> userTrades = new ArrayList<UserTrade>();
        try {
            Date queryTime = timeRetrieveService.getAndSyncQueryTime();
            log.info("Trade search since " + queryTime.toString());

            for (CurrencyPair pair : pollingTradeService.getExchangeSymbols()) {
                DefaultTradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair = new DefaultTradeHistoryParamCurrencyPair();
                tradeHistoryParamCurrencyPair.setCurrencyPair(pair);

                UserTrades tradeHistory = pollingTradeService.getTradeHistory(tradeHistoryParamCurrencyPair);
                for (UserTrade trade : tradeHistory.getUserTrades()) {
                    if (trade.getTimestamp().after(queryTime)) {
                        userTrades.add(trade);
                    }
                }
            }

        } catch (IOException e) {
            log.debug("Error When Getting History: ", e);
        }

        return userTrades;
    }
}
