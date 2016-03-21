package work.hoodie.crypto.exchange.monitor;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@Slf4j
public class NotificationTest {

    @Value("${notification.test:false}")
    private Boolean testNotification;


    @Autowired
    @Qualifier("CorrectNotifierService")
    private NotifierService notifierService;

    @PostConstruct
    public void test() {
        UserTrade debugMessage = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .feeCurrency("TEST")
                .price(BigDecimal.valueOf(1))
                .feeAmount(BigDecimal.valueOf(0))
                .currencyPair(new CurrencyPair("TEST", "NOTIFICATION"))
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        if (testNotification) {
            log.info("Sending Test Notification...");
            notifierService.notify(debugMessage);
        }
    }
}
