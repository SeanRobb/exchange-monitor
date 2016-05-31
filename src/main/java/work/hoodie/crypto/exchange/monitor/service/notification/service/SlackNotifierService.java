package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.SlackMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.SlackMessageSenderService;

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

    public void notify(UserTrade userTrade) {
        SlackMessage build = slackMessageBuilderService.build(userTrade);
        slackMessageSenderService.send(build);
    }

    public void notify(WalletComparisonSummary walletComparisonSummary) {
        SlackMessage build = slackMessageBuilderService.build(walletComparisonSummary);
        slackMessageSenderService.send(build);
    }
}
