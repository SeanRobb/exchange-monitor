package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.EmailMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.EmailMessageSenderService;

@Component
public class EmailNotifierService implements NotifierService {
    @Autowired
    private EmailMessageBuilderService emailMessageBuilderService;
    @Autowired
    private EmailMessageSenderService emailMessageSenderService;

    public void notify(String message) {
        EmailMessage build = emailMessageBuilderService.build(message);
        emailMessageSenderService.send(build);
    }

    public void notify(UserTrade userTrade) {
        EmailMessage build = emailMessageBuilderService.build(userTrade);
        emailMessageSenderService.send(build);
    }

    public void notify(WalletComparisonSummary walletComparisonSummary) {
        EmailMessage build = emailMessageBuilderService.build(walletComparisonSummary);
        emailMessageSenderService.send(build);
    }
}
