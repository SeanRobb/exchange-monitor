package work.hoodie.crypto.exchange.monitor.service.trade.open;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;

import java.math.BigDecimal;

@Component
public class OpenOrderMarshaller {
    public OpenOrder convert(LimitOrder limitOrder) {
        CurrencyPair currencyPair = limitOrder.getCurrencyPair();
        OpenOrder openOrder = new OpenOrder();
        if (limitOrder.getType() == Order.OrderType.BID) {
            BigDecimal amount = limitOrder.getTradableAmount().multiply(limitOrder.getLimitPrice());
            openOrder.setAmount(amount);
            openOrder.setCurrency(currencyPair.counterSymbol);
        } else {
            openOrder.setAmount(limitOrder.getTradableAmount());
            openOrder.setCurrency(currencyPair.baseSymbol);
        }
        return openOrder;
    }
}
