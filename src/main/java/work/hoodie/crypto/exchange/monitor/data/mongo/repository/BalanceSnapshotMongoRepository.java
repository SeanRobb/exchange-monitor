package work.hoodie.crypto.exchange.monitor.data.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import work.hoodie.crypto.exchange.monitor.domain.BalanceSnapshot;

public interface BalanceSnapshotMongoRepository extends MongoRepository<BalanceSnapshot, String> {
}
