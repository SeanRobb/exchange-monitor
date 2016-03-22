package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.TradeConverter;

import java.math.BigDecimal;

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

        OrderType type = userTrade.getType();
        BigDecimal amount = userTrade.getTradableAmount();
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        BigDecimal price = userTrade.getPrice();


        BigDecimal feeAmount = userTrade.getFeeAmount();
        String feeCurrency = userTrade.getFeeCurrency();
        BigDecimal total = amount.multiply(price);
        BigDecimal feesInCoins = price.multiply(feeAmount);
        BigDecimal actualAmount = total.subtract(feesInCoins);

        String message = actualAmount + " " + currencyPair.baseSymbol + " " + typeConvert(type) +
                " for " + price + " " + currencyPair.counterSymbol +
                " \n Fees Payed: " + feeAmount + " " + feeCurrency +
                " \n Total Payed: " + total + " " + feeCurrency;
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
