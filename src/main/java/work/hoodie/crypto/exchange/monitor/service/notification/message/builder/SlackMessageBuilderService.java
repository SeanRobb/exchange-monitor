package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;

@Component
public class SlackMessageBuilderService {

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private MessageBodyBuilderService messageBodyBuilderService;

    private final String icon_emoji = ":moneybag:";


    public SlackMessage build(UserTrade userTrade) {
        String username = exchangeSpecification.getExchangeName() + " Monitor";

        String message = messageBodyBuilderService.build(userTrade);

        return new SlackMessage(message, icon_emoji, username);
    }


}
