package work.hoodie.exchange.monitor.service.trade.recent;


import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.RecentUserTrade;

import java.util.List;

/**
 * Created by ryantodd on 2/2/17.
 */
@Component
public class RecentUserTradeMarshaller {
    public List<RecentUserTrade> convert(List<UserTrade> userTrades) {
        return null;
    }

    public RecentUserTrade convert(UserTrade userTrades) {
        return null;
    }
}
