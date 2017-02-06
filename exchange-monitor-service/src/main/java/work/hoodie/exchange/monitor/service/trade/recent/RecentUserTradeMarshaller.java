package work.hoodie.exchange.monitor.service.trade.recent;


import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.RecentUserTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ryantodd on 2/2/17.
 */
@Component
public class RecentUserTradeMarshaller {
    public List<RecentUserTrade> convert(List<UserTrade> userTrades) {
        List<RecentUserTrade> recentUserTrade = new ArrayList<>();
        for(RecentUserTrade recentTrade : recentUserTrade){
            for(UserTrade userTrade : userTrades){
                recentTrade.setTradeId(userTrade.getId());
                recentTrade.setCurrencyPair(userTrade.getCurrencyPair());
                recentTrade.setPrice(userTrade.getPrice());
                recentTrade.setTimestamp(userTrade.getTimestamp());
                recentTrade.setType(userTrade.getType());
                recentUserTrade.add(recentTrade);
            }

        }return recentUserTrade;
    }

    public RecentUserTrade convert(UserTrade userTrade) {
        RecentUserTrade recentUserTrade = new RecentUserTrade()
                .setTradeId(userTrade.getId())
                .setCurrencyPair(userTrade.getCurrencyPair())
                .setPrice(userTrade.getPrice())
                .setTimestamp(userTrade.getTimestamp())
                .setType(userTrade.getType());
        return recentUserTrade;

    }
}
