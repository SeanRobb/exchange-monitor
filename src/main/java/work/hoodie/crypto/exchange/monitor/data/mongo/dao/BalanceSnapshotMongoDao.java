package work.hoodie.crypto.exchange.monitor.data.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.data.common.dao.BalanceSnapshotDao;
import work.hoodie.crypto.exchange.monitor.domain.BalanceSnapshot;
import work.hoodie.crypto.exchange.monitor.data.mongo.repository.BalanceSnapshotMongoRepository;

@Component
public class BalanceSnapshotMongoDao implements BalanceSnapshotDao {

    @Autowired
    private BalanceSnapshotMongoRepository balanceSnapshotMongoRepository;

    public BalanceSnapshot save(BalanceSnapshot balanceSnapshot) {
        return balanceSnapshotMongoRepository.save(balanceSnapshot);
    }

    public void delete(String id) {
        balanceSnapshotMongoRepository.delete(id);
    }

}
