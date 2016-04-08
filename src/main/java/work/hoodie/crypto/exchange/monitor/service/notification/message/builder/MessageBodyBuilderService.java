package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

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

    public String build(WalletSummary walletSummary) {
        String message = "Summary:\n";
        for (WalletComparison walletComparison : walletSummary.getWalletComparisons()) {
            message += "\tCurrency: " + walletComparison.getCurrency() +
                    "\n\t\tBalance:\t\t" + walletComparison.getBalance().stripTrailingZeros().toPlainString() +
                    "\n\t\tBTC Value:\t\t" + walletComparison.getBtcValue().stripTrailingZeros().toPlainString() +
                    "\n\t\tBalance Gain:\t" + walletComparison.getBalanceGain().stripTrailingZeros().toPlainString() +
                    "\n\t\tBTC Value Gain:\t" + walletComparison.getBtcValueGain().stripTrailingZeros().toPlainString() + " BTC" + "\n";
        }
        message += "\nTotal BTC Change: " + walletSummary.getBtcTotalChange().stripTrailingZeros().toPlainString() + " BTC";

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
