package work.hoodie.exchange.monitor.service.trade.recent;


import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.RecentUserTrade;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RecentUserTradeMarshaller {

    public List<RecentUserTrade> convert(List<UserTrade> userTrades) {

        List<RecentUserTrade> recentUserTrade = userTrades
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return recentUserTrade;
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
