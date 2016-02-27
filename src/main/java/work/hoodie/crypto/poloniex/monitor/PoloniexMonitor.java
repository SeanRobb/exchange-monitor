package work.hoodie.crypto.poloniex.monitor;

import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.poloniex.monitor.domain.SlackMessage;
import work.hoodie.crypto.poloniex.monitor.service.RecentTradesService;
import work.hoodie.crypto.poloniex.monitor.service.SlackMessageBuilderService;
import work.hoodie.crypto.poloniex.monitor.service.SlackNotifierService;

import java.util.List;

@Component
@Slf4j
public class PoloniexMonitor {

    @Autowired
    private RecentTradesService recentTradesService;

    @Autowired
    private SlackNotifierService slackNotifierService;

    @Autowired
    private SlackMessageBuilderService slackMessageBuilderService;


    @Scheduled(cron = "0 */1 * * * *")
    public void check() {
        List<UserTrade> history = recentTradesService.getHistory();

        if (!history.isEmpty())
            log.info(history.toString());
        else
            log.debug("No Trades");

        for (UserTrade trade : history) {
            SlackMessage message = slackMessageBuilderService.build(trade);
            slackNotifierService.notify(message);
        }

    }
}
