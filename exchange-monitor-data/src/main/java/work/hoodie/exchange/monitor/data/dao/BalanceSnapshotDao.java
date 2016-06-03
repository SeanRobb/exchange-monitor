package work.hoodie.exchange.monitor.data.dao;

import work.hoodie.exchange.monitor.common.BalanceSnapshot;

public interface BalanceSnapshotDao {

    BalanceSnapshot save(BalanceSnapshot balanceSnapshot);

    void delete(String id);

}
