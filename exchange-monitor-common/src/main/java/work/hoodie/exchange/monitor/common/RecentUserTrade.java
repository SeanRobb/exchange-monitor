package work.hoodie.exchange.monitor.common;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Accessors(chain=true)
@Document(collection ="userTrades")
@AllArgsConstructor
@NoArgsConstructor
public class RecentUserTrade {
    @Id
    private String id;
    private String tradeId;
    private String instanceId;
    private CurrencyPair currencyPair;
    private BigDecimal price;
    private Date timestamp;
    private BigDecimal tradableAmount;
    private Order.OrderType type;
}