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
    private TradeConverter tradeConverter;
    @Value("${email.address:}")
    private String emailAddress;

    private final String purchased = "purchased";
    private final String sold = "sold";

    public EmailMessage build(UserTrade userTrade) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject(exchangeSpecification.getExchangeName() + " Monitor");

        Order.OrderType type = userTrade.getType();
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
        emailMessage.setContent(message);

        emailMessage.setFromEmailAddress(fromEmailAddress);

        emailMessage.setToEmailAddress(emailAddress);

        return emailMessage;
    }


    private String typeConvert(Order.OrderType type) {
        String tradeType;
        if (type == Order.OrderType.BID) {
            tradeType = purchased;
        } else if (type == Order.OrderType.ASK) {
            tradeType = sold;
        } else {
            tradeType = "";
        }
        return tradeType;
    }
}
