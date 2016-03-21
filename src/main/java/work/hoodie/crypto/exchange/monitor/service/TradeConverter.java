package work.hoodie.crypto.exchange.monitor.service;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TradeConverter {

    public BigDecimal getTotal(UserTrade userTrade) {
        return userTrade.getPrice().multiply(userTrade.getTradableAmount());
    }

    public BigDecimal getCoinSent(UserTrade userTrade) {
        if (Order.OrderType.ASK == userTrade.getType()) {
            return userTrade.getPrice().multiply(userTrade.getTradableAmount());
        } else {
            return userTrade.getTradableAmount();
        }
    }

    public BigDecimal getCoinReceived(UserTrade userTrade) {
        String feeCurrency = userTrade.getFeeCurrency();
        String currencyPairBase = userTrade.getCurrencyPair().baseSymbol;

        if (feeCurrency.equalsIgnoreCase(currencyPairBase)) {
            BigDecimal fee = userTrade.getFeeAmount();
            return userTrade.getTradableAmount().subtract(fee);
        }

        BigDecimal totalMinusFees = getTotal(userTrade)
                .subtract(userTrade.getFeeAmount());

        if (Order.OrderType.ASK == userTrade.getType()) {
            return totalMinusFees;
        } else {
            BigDecimal pricePerCoin = userTrade.getPrice();
            return totalMinusFees.divide(pricePerCoin, 8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
        }
    }

    public String getCoinReceivedName(UserTrade userTrade) {
        CurrencyPair currencyPair = userTrade.getCurrencyPair();

        if (Order.OrderType.ASK == userTrade.getType()) {
            return currencyPair.counterSymbol;
        } else {
            return currencyPair.baseSymbol;
        }
    }

    public String getCoinSentName(UserTrade userTrade) {
        CurrencyPair currencyPair = userTrade.getCurrencyPair();

        if (Order.OrderType.ASK == userTrade.getType()) {
            return currencyPair.baseSymbol;
        } else {
            return currencyPair.counterSymbol;
        }
    }
}
