package work.hoodie.crypto.exchange.monitor;

import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.SimpleRecentTradesService;
import work.hoodie.crypto.exchange.monitor.service.SlackMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.SlackNotifierService;

import java.util.List;

@Component
@Slf4j
public class ExchangeMonitor {

    @Autowired
    private SimpleRecentTradesService simpleRecentTradesService;

    @Autowired
    private SlackNotifierService slackNotifierService;

    @Autowired
    private SlackMessageBuilderService slackMessageBuilderService;


    @Scheduled(cron = "0 */1 * * * *")
    public void check() {
        List<UserTrade> history = simpleRecentTradesService.getHistory();

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
