package work.hoodie.exchange.monitor.data.dao;


import work.hoodie.exchange.monitor.common.RecentUserTrade;

import java.util.List;

public interface UserTradeDao {

    RecentUserTrade save(List<RecentUserTrade> recentUserTrade);

    void delete(String id);

}