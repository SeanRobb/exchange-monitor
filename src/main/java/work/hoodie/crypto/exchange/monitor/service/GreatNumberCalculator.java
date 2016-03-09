package work.hoodie.crypto.exchange.monitor.service;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.xeiam.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;

/**
 * Created by ryantodd on 3/8/16.
 */
public class GreatNumberCalculator {
    public BigDecimal getTotal(UserTrade userTrade){
        BigDecimal total = userTrade.getPrice().multiply(userTrade.getTradableAmount());
        return total;
    }

//    This function is the total cost for a trade
//    EX1:  I bought 10 LTC for 100 USD with a fee of 1 USD
//          Coin sent will return 100 (USD)
//          Coin received is 9 (LTC)

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
        return null;
    }
    public BigDecimal getCoinRecieved(UserTrade userTrade) {
        return null;
    }
}
