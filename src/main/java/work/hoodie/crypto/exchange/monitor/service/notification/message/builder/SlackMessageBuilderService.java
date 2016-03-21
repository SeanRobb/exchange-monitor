package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.TradeConverter;

@Component
public class SlackMessageBuilderService {

    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private TradeConverter tradeConverter;

    private final String icon_emoji = ":moneybag:";
    private String username;
    private final String purchased = "purchased";
    private final String sold = "sold";


    public SlackMessage build(UserTrade userTrade) {
        username = exchangeSpecification.getExchangeName() + " Monitor";

        String message = "Received " +
                tradeConverter.getCoinReceived(userTrade).toPlainString() + " " +
                tradeConverter.getCoinReceivedName(userTrade) + " for " +
                tradeConverter.getCoinSent(userTrade).toPlainString() + " " +
                tradeConverter.getCoinSentName(userTrade) + " with "
                + userTrade.getFeeAmount().toPlainString() + " " + userTrade.getFeeCurrency() + " in fees.";

        return new SlackMessage(message, icon_emoji, username);
    }

}
