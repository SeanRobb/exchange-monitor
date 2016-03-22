package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.service.TradeConverter;

import java.math.BigDecimal;

@Component
public class EmailMessageBuilderService {

    private final String fromEmailAddress = "ExchangeMonitor@workhoodie.com";

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private MessageBodyBuilderService messageBodyBuilderService;

    @Value("${email.address:}")
    private String emailAddress;

    public EmailMessage build(UserTrade userTrade) {
        String subject = exchangeSpecification.getExchangeName() + " Monitor";
        String message = messageBodyBuilderService.build(userTrade);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject(subject);
        emailMessage.setContent(message);
        emailMessage.setFromEmailAddress(fromEmailAddress);
        emailMessage.setToEmailAddress(emailAddress);
        return emailMessage;
    }

}
