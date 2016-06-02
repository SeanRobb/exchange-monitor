package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "balanceSnapshots")
public class BalanceSnapshot {
    @Id
    private String id;
    private String instanceId;
    private Date date;
    private List<Balance> balances;
}
