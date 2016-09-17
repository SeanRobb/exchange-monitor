package work.hoodie.exchange.monitor.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.SlackMessage;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.notification.builder.SlackMessageBuilderService;
import work.hoodie.exchange.monitor.notification.sender.SlackMessageSenderService;

import java.util.List;

@Component
public class SlackNotifierService implements NotifierService {
    @Autowired
    private SlackMessageBuilderService slackMessageBuilderService;
    @Autowired
    private SlackMessageSenderService slackMessageSenderService;

    public void notify(String message) {
        SlackMessage build = slackMessageBuilderService.build(message);
        slackMessageSenderService.send(build);
    }

    public void notify(List<UserTrade> userTrade) {
        SlackMessage build = slackMessageBuilderService.build(userTrade);
        slackMessageSenderService.send(build);
    }

    public void notify(WalletComparisonSummary walletComparisonSummary) {
        SlackMessage build = slackMessageBuilderService.build(walletComparisonSummary);
        slackMessageSenderService.send(build);
    }
}
