package work.hoodie.crypto.exchange.monitor.service;

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
//    public BigDecimal getPostFeeAmount {
//        BigDecimal
//    }
}
