//package work.hoodie.exchange.monitor.data.dao;
//
//import work.hoodie.exchange.monitor.common.RecentUserTrade;
//import work.hoodie.exchange.monitor.data.repository.UserTradeMongoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
///**
// * Created by ryantodd on 2/1/17.
// */
//@Component
//public class UserTradeMongoDao implements UserTradeDao{
//
//    @Autowired
//    private UserTradeMongoRepository userTradeMongoRepository;
//
//    public RecentUserTrade save(RecentUserTrade recentUserTrade) {
//
//        return userTradeMongoRepository.save(recentUserTrade);
//    }
//
//    public void delete(String id) {
//        userTradeMongoRepository.delete(id);
//    }
//
//}
