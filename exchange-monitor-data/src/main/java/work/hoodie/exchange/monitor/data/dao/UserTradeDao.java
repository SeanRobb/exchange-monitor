package work.hoodie.exchange.monitor.data.dao;


import com.xeiam.xchange.dto.trade.UserTrade;
import work.hoodie.exchange.monitor.common.RecentUserTrade;

import java.util.List;

public interface UserTradeDao {
    RecentUserTrade save(List<UserTrade> userTradeList);

    void delete(String id);

}