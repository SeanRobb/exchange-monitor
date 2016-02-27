package work.hoodie.crypto.exchange.monitor.service;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;

import java.math.BigDecimal;

@Component
public class SlackMessageBuilderService {

    private final String icon_emoji = ":moneybag:";
    private final String username = "Poloniex Monitor";
    private final String purchased = "purchased";
    private final String sold = "sold";

    public SlackMessage build(UserTrade userTrade) {
        OrderType type = userTrade.getType();
        BigDecimal amount = userTrade.getTradableAmount();
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        BigDecimal price = userTrade.getPrice();

        BigDecimal feeAmount = userTrade.getFeeAmount();
        String feeCurrency = userTrade.getFeeCurrency();

        String message = amount + " " + currencyPair.baseSymbol
                + " " + typeConvert(type) + " for "
                + price + " " + currencyPair.counterSymbol +
                "\\nFees Payed: " + feeAmount + " " + feeCurrency;
        return new SlackMessage(message, icon_emoji, username);
    }

    private String typeConvert(OrderType type) {
        String tradeType;
        if (type == OrderType.BID) {
            tradeType = purchased;
        } else if (type == OrderType.ASK) {
            tradeType = sold;
        } else {
            tradeType = "";
        }
        return tradeType;
    }
}
