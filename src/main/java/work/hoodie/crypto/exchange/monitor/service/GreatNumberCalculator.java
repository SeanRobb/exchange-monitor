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

//    This function is the total cost for a trade
//    EX1:  I bought 10 LTC for 100 USD with a fee of 1 USD
//          Coin sent will return 1000 (USD)
//          Coin received is 9.9 (LTC)

//          EX1 UserTrade will look like this
//          userTrade.getType() : BID
//          userTrade.getFeeAmount(): 1
//          userTrade.getFeeCurrency(): USD
//          userTrade.getTradableAmount() : 10
//          userTrade.getPrice() : 100
//          userTrade.getCurrencyPair() : LTC/USD
//
//    EX2:  I sold 1 LTC for 100 USD with a fee of 10 USD
//          Coin sent will be 1 (LTC)
//          Coin received will be 90 (USD)

//          EX2 UserTrade will look like this
//          userTrade.getType() : ASK
//          userTrade.getFeeAmount(): 10
//          userTrade.getFeeCurrency(): USD
//          userTrade.getTradableAmount() : 1
//          userTrade.getPrice() : 100
//          userTrade.getCurrencyPair() : LTC/USD

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
