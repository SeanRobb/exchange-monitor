package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

@Component
public class EmailMessageBuilderService {

    public final String fromEmailAddress = "ExchangeMonitor@workhoodie.com";

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private MessageBodyBuilderService messageBodyBuilderService;

    @Value("${email.address:}")
    private String toEmailAddress;

    public EmailMessage build(UserTrade userTrade) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor Trade Executed";
        String body = messageBodyBuilderService.build(userTrade);

        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }

    public EmailMessage build(WalletSummary walletSummary) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor Wallet Summary";
        String body = messageBodyBuilderService.build(walletSummary);

        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }

    public EmailMessage build(String body) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor Notification Test";
        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }
}
