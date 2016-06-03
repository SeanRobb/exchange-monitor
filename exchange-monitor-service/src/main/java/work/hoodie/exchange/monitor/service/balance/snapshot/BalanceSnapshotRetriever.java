package work.hoodie.exchange.monitor.service.balance.snapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.BalanceSnapshot;
import work.hoodie.exchange.monitor.service.balance.BalanceRetrieverService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class BalanceSnapshotRetriever {

    @Autowired
    private BalanceRetrieverService balanceRetrieverService;

    @Value("${instance.id:default}")
    private String instanceId;

    public BalanceSnapshot getCurrentSnapshot() throws IOException {
        List<Balance> balances = balanceRetrieverService.getBalances();
        return new BalanceSnapshot()
                .setBalances(balances)
                .setDate(new Date())
                .setInstanceId(instanceId);
    }

}
