package work.hoodie.exchange.monitor.notification.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.SlackMessage;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;

import java.util.List;

@Component
public class SlackMessageBuilderService {

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private MessageBodyBuilderService messageBodyBuilderService;

    private final String icon_emoji = ":moneybag:";

    public SlackMessage build(String message) {
        String username = exchangeSpecification.getExchangeName() + " Monitor";

        return new SlackMessage(message, icon_emoji, username);
    }

    public SlackMessage build(UserTrade userTrade) {
        String username = exchangeSpecification.getExchangeName() + " Monitor";

        String message = messageBodyBuilderService.build(userTrade);

        return new SlackMessage(message, icon_emoji, username);
    }

    public SlackMessage build(List<UserTrade> userTrade) {
        String username = exchangeSpecification.getExchangeName() + " Monitor";

        String message = "";

        if (userTrade.size() > 1)
            message = message.concat(userTrade.size() + " Trades Executed\n\n");

        message = message.concat(messageBodyBuilderService.build(userTrade));

        return new SlackMessage(message, icon_emoji, username);
    }

    public SlackMessage build(WalletComparisonSummary walletComparisonSummary) {
        String username = exchangeSpecification.getExchangeName() + " Monitor";

        String message = messageBodyBuilderService.build(walletComparisonSummary);

        return new SlackMessage(message, icon_emoji, username);
    }
}
