package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;

import java.math.BigDecimal;

@Component
public class MessageBodyBuilderService {

    private final String purchased = "purchased";
    private final String sold = "sold";

    public String build(UserTrade userTrade) {
        Order.OrderType type = userTrade.getType();
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        BigDecimal price = userTrade.getPrice();


        BigDecimal feeAmount = userTrade.getFeeAmount();
        String feeCurrency = userTrade.getFeeCurrency();

        return userTrade.getTradableAmount() + " " + currencyPair.baseSymbol + " " + typeConvert(type) +
                " for " + price + " " + currencyPair.counterSymbol +
                " \n Fees Payed: " + feeAmount + " " + feeCurrency;
    }

    public String build(WalletComparisonSummary walletComparisonSummary) {
        String message = "Summary:\n";
        for (WalletComparison walletComparison : walletComparisonSummary.getWalletComparisons()) {
            message += walletComparison.toString();
        }
        message += "\n" + walletComparisonSummary.toString();

        return message;
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
