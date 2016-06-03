package work.hoodie.exchange.monitor.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.BalanceSnapshot;
import work.hoodie.exchange.monitor.data.repository.BalanceSnapshotMongoRepository;

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
