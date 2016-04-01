package work.hoodie.crypto.exchange.monitor.service.trade.open;

import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenOrderRetriever {
    @Autowired
    private PollingTradeService pollingTradeService;
    @Autowired
    private OpenOrderMarshaller openOrderMarshaller;

    public List<OpenOrder> getOpenOrders() throws IOException {
        OpenOrders openOrders = pollingTradeService.getOpenOrders();
        List<OpenOrder> openOrderList = new ArrayList<OpenOrder>();
        for (LimitOrder limitOrder :  openOrders.getOpenOrders()) {
            OpenOrder convert = openOrderMarshaller.convert(limitOrder);
            openOrderList.add(convert);
        }
        return openOrderList;
    }
}
