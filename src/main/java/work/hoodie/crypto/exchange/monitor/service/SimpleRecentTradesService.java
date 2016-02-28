package work.hoodie.crypto.exchange.monitor.service;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SimpleRecentTradesService {

    private Date startTime;

    @Autowired
    private PollingTradeService pollingTradeService;

    @PostConstruct
    public void init() {
        setStartTime();
    }

    private void setStartTime() {
        startTime = DateTime.now().toDate();
    }

    public List<UserTrade> getHistory() {
        List<UserTrade> userTrades = new ArrayList<UserTrade>();
        try {
            DefaultTradeHistoryParamsTimeSpan tradeHistoryParams = new DefaultTradeHistoryParamsTimeSpan(startTime);
//            DefaultTradeHistoryParamPaging defaultTradeHistoryParamPaging = new DefaultTradeHistoryParamPaging();
//            defaultTradeHistoryParamPaging.setPageLength(1000);
//            defaultTradeHistoryParamPaging.setPageNumber(0);

            DefaultTradeHistoryParamCurrencyPair defaultTradeHistoryParamPaging = new DefaultTradeHistoryParamCurrencyPair();
            defaultTradeHistoryParamPaging.setCurrencyPair(CurrencyPair.BTC_USD);
            log.info("Trade search since "+ startTime.toString());
            setStartTime();
            UserTrades tradeHistory = pollingTradeService.getTradeHistory(defaultTradeHistoryParamPaging);
            userTrades = tradeHistory.getUserTrades();
        } catch (IOException e) {
            log.debug("Error When Getting History: ", e);
        }

        return userTrades;
    }
}
