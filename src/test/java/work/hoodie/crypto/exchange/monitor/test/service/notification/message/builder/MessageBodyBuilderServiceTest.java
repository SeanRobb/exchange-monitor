package work.hoodie.crypto.exchange.monitor.test.service.notification.message.builder;


import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.MessageBodyBuilderService;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MessageBodyBuilderServiceTest {

    @InjectMocks
    private MessageBodyBuilderService classUnderTest;

    @Test
    public void testBuild() throws Exception {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        String message = classUnderTest.build(userTrade);

        assertNotNull(message);
    }
}
