import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.SlackMessageBuilderService;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlackMessageBuilderServiceTest {
    @InjectMocks
    private SlackMessageBuilderService slackMessageBuilderService;
    @Mock
    private ExchangeSpecification exchangeSpecification;

    private final String expectedEmoji = ":moneybag:";
    private final String expectedUsername = "Poloniex Monitor";

    @Before
    public void init() {
        when(exchangeSpecification.getExchangeName()).thenReturn("Poloniex");
    }

    @Test
    public void testBuild_bid() throws Exception {
        UserTrade userTrade = new UserTrade.Builder()
                .type(OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        SlackMessage build = slackMessageBuilderService.build(userTrade);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals("10 DOGE purchased for 1 LTC \n " +
                "Fees Payed: 0 LTC", build.getText());
    }

    @Test
    public void testBuild_ask() throws Exception {
        UserTrade userTrade = new UserTrade.Builder()
                .type(OrderType.ASK)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        SlackMessage build = slackMessageBuilderService.build(userTrade);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals("10 DOGE sold for 1 LTC \n " +
                "Fees Payed: 0 LTC", build.getText());
    }
}
