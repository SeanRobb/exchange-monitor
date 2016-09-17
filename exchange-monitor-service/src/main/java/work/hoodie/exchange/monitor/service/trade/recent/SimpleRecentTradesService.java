package work.hoodie.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SimpleRecentTradesService implements RecentTradesService {

    @Autowired
    private PollingTradeService pollingTradeService;
    @Autowired
    private TimeRetrieveService timeRetrieveService;

    public List<UserTrade> getHistory() {
        List<UserTrade> userTrades = new ArrayList<UserTrade>();

        try {
            Date queryTime = timeRetrieveService.getAndSyncQueryTime();
            DefaultTradeHistoryParamsTimeSpan tradeHistoryParams = new DefaultTradeHistoryParamsTimeSpan(queryTime);
            log.info("Trade search since " + queryTime.toString());
            UserTrades tradeHistory = pollingTradeService.getTradeHistory(tradeHistoryParams);

            userTrades = tradeHistory.getUserTrades();
        } catch (IOException e) {
            log.debug("Error When Getting History: ", e);
        }

        return userTrades;
    }
}
