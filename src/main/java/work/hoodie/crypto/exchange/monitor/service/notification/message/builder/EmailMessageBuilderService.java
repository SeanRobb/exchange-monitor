package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;

@Component
public class EmailMessageBuilderService {
    public EmailMessage build(UserTrade userTrade) {
        return null;
    }
}
