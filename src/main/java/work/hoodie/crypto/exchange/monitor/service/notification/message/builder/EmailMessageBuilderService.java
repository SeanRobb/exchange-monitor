package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.service.GreatNumberCalculator;

@Component
public class EmailMessageBuilderService {

    private final String fromEmailAddress = "ExchangeMonitor@workhoodie.com";

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private GreatNumberCalculator greatNumberCalculator;
    @Value("${email.address:}")
    private String emailAddress;

    public EmailMessage build(UserTrade userTrade) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject(exchangeSpecification.getExchangeName() + " Monitor");

        emailMessage.setContent("Received " +
                greatNumberCalculator.getCoinReceived(userTrade) + " " +
                greatNumberCalculator.getCoinReceivedName(userTrade) + " for " +
                greatNumberCalculator.getCoinSent(userTrade) + " " +
                greatNumberCalculator.getCoinSentName(userTrade) + " with "
                + userTrade.getFeeAmount() + " " + userTrade.getFeeCurrency() + " in fees.");

        emailMessage.setFromEmailAddress(fromEmailAddress);

        emailMessage.setToEmailAddress(emailAddress);

        return emailMessage;
    }
}
