package work.hoodie.crypto.exchange.monitor.data.common.dao;

import work.hoodie.crypto.exchange.monitor.domain.BalanceSnapshot;

public interface BalanceSnapshotDao {

    BalanceSnapshot save(BalanceSnapshot balanceSnapshot);

    void delete(String id);

}
