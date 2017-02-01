package work.hoodie.exchange.monitor.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain=true)
@Document(collection ="userTrades")
@AllArgsConstructor
@NoArgsConstructor
public class UserTrade {
    private String text;
    private String instanceId;
    private String username ;
}