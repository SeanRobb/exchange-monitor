package work.hoodie.exchange.monitor.service.trade.open;

import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.OpenOrder;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OpenOrderService {

    public OpenOrder findTotalOpenOrdersForCurrency(List<OpenOrder> openOrderList, String currency) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OpenOrder openOrder : openOrderList) {
            String currentOrderCurrency = openOrder.getCurrency();
            if (currentOrderCurrency.equals(currency)) {
                totalAmount = totalAmount.add(openOrder.getAmount());
            }
        }

        return new OpenOrder()
                .setCurrency(currency)
                .setAmount(totalAmount);

    }

}
