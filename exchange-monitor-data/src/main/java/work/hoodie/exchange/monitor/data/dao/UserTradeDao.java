package work.hoodie.exchange.monitor.data.dao;


import work.hoodie.exchange.monitor.common.UserTrade;

public interface UserTradeDao {

    UserTrade save(UserTrade userTrade);

    void delete(String id);

}