package work.hoodie.exchange.monitor.notification.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.EmailMessage;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;

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

    public EmailMessage build(WalletComparisonSummary walletComparisonSummary) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor Wallet Summary";
        String body = messageBodyBuilderService.build(walletComparisonSummary);

        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }

    public EmailMessage build(String body) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor Notification Test";
        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }
}
