package work.hoodie.crypto.exchange.monitor.service;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GreatNumberCalculator {
    public BigDecimal getTotal(UserTrade userTrade) {
        BigDecimal total = userTrade.getPrice().multiply(userTrade.getTradableAmount());
        return total;
    }

    public BigDecimal getCoinSent(UserTrade userTrade) {
        if (Order.OrderType.ASK == userTrade.getType()) {
            return userTrade.getPrice().multiply(userTrade.getTradableAmount());
        } else {
            return userTrade.getTradableAmount();
        }

    }

    public BigDecimal getCoinReceived(UserTrade userTrade) {
        if (userTrade.getFeeCurrency().equalsIgnoreCase(userTrade.getCurrencyPair().baseSymbol)) {
            return userTrade.getTradableAmount().subtract(userTrade.getFeeAmount());
        }

        BigDecimal amount = userTrade.getPrice().multiply(userTrade.getTradableAmount());

        if (Order.OrderType.ASK == userTrade.getType()) {
            return amount.subtract((userTrade.getFeeAmount()));
        } else {
            return amount.subtract(userTrade.getFeeAmount()).divide(userTrade.getPrice());
        }
    }

    public String getCoinReceivedName(UserTrade userTrade) {
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        if (Order.OrderType.ASK == userTrade.getType()){
            return currencyPair.counterSymbol;
        } else {
            return currencyPair.baseSymbol;
        }
    }

    public String getCoinSentName(UserTrade userTrade) {
        CurrencyPair currencyPair = userTrade.getCurrencyPair();
        if (Order.OrderType.ASK == userTrade.getType()){
            return currencyPair.baseSymbol;
        } else {
            return currencyPair.counterSymbol;
        }
    }
}
