package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.SlackMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.SlackMessageSenderService;

@Component
public class SlackNotifierService implements NotifierService {
    @Autowired
    private SlackMessageBuilderService slackMessageBuilderService;
    @Autowired
    private SlackMessageSenderService slackMessageSenderService;

    public void notify(UserTrade userTrade) {

    }
}
