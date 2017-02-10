package work.hoodie.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.RecentUserTrade;
import work.hoodie.exchange.monitor.data.dao.UserTradeDao;

import java.util.List;

/**
 * Created by ryantodd on 2/2/17.
 */
@Component
public class UserTradeService {
    @Autowired
    private UserTradeDao userTradeDao;

    @Autowired
    private RecentUserTradeMarshaller recentUserTradeMarshaller;

    public void save(List<UserTrade> userTrades) {
        List<RecentUserTrade> convert = recentUserTradeMarshaller.convert(userTrades);
        for (RecentUserTrade usuck : convert) {
            userTradeDao.save(usuck);
        }
    }

}
