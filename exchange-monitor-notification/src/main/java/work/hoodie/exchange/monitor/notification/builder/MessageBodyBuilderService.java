package work.hoodie.exchange.monitor.notification.builder;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.WalletComparison;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.notification.formatter.PriceFormatter;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MessageBodyBuilderService {

    public static final String NEW_LINE = "\n";
    private final String purchased = "purchased";
    private final String sold = "sold";

    @Autowired
    private PriceFormatter priceFormatter;


    public String build(UserTrade userTrade) {
        Order.OrderType type = userTrade.getType();
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        BigDecimal price = userTrade.getPrice();
        BigDecimal tradableAmount = userTrade.getTradableAmount();
        BigDecimal feeAmount = userTrade.getFeeAmount();
        String feeCurrency = userTrade.getFeeCurrency();

        String formattedPrice = priceFormatter.getFormattedPriceString(price, currencyPair.counterSymbol);

        String formattedFee = priceFormatter.getFormattedPriceString(feeAmount, feeCurrency);

        String formattedTradeable = priceFormatter.getFormattedPriceString(tradableAmount, currencyPair.baseSymbol);

        return buildMessage(type, formattedPrice, formattedFee, formattedTradeable);
    }

    public String build(List<UserTrade> userTradeList) {
        return userTradeList.stream()
                .map(this::build)
                .map(NEW_LINE::concat)
                .map(NEW_LINE::concat)
                .reduce(String::concat)
                .orElse("")
                .replaceFirst(NEW_LINE, "")
                .replaceFirst(NEW_LINE, "");
    }

    public String build(WalletComparisonSummary walletComparisonSummary) {
        String message = "Summary:\n";
        for (WalletComparison walletComparison : walletComparisonSummary.getWalletComparisons()) {
            message += walletComparison.toString();
        }
        message += NEW_LINE + walletComparisonSummary.toString();

        return message;
    }

    private String buildMessage(Order.OrderType type, String price, String fee, String tradeable) {
        return tradeable + " " + typeConvert(type) + " for " + price + NEW_LINE + " Fees Payed: " + fee;
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
