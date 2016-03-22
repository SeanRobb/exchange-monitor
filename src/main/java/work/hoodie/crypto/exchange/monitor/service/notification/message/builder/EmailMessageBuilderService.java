package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;

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
        String subject = exchangeSpecification.getExchangeName() + " Monitor";
        String body = messageBodyBuilderService.build(userTrade);

        return new EmailMessage(fromEmailAddress, toEmailAddress, subject, body);
    }

}
