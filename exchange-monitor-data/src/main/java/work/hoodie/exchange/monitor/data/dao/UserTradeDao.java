package work.hoodie.exchange.monitor.data.dao;


import work.hoodie.exchange.monitor.common.RecentUserTrade;

public interface UserTradeDao {
    RecentUserTrade save(RecentUserTrade recentUserTrade);

    void delete(String id);

}