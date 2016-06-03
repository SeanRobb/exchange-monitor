package work.hoodie.exchange.monitor.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import work.hoodie.exchange.monitor.common.BalanceSnapshot;

public interface BalanceSnapshotMongoRepository extends MongoRepository<BalanceSnapshot, String> {
}
