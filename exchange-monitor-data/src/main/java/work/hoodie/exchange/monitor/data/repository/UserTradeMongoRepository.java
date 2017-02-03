/**
 * Created by ryantodd on 2/1/17.
 */
package work.hoodie.exchange.monitor.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import work.hoodie.exchange.monitor.common.RecentUserTrade;


public interface UserTradeMongoRepository extends MongoRepository<RecentUserTrade, String> {
}