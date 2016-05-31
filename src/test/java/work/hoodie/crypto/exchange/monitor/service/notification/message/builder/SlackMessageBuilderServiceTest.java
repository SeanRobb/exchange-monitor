package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;

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
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlackMessageBuilderServiceTest {
    @InjectMocks
    private SlackMessageBuilderService slackMessageBuilderService;

    @Mock
    private ExchangeSpecification exchangeSpecification;
    @Mock
    private MessageBodyBuilderService messageBodyBuilderService;
    @Mock
    private WalletComparisonSummary walletComparisonSummary;
    private final String expectedEmoji = ":moneybag:";

    private final String expectedUsername = "Poloniex Monitor";

    @Before
    public void init() {
        when(exchangeSpecification.getExchangeName()).thenReturn("Poloniex");
    }

    @Test
    public void build() throws Exception {
        String message = "Message";

        SlackMessage build = slackMessageBuilderService.build(message);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals(message, build.getText());
    }

    @Test
    public void testBuild_bid() throws Exception {
        String message = "My Message";
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

        when(messageBodyBuilderService.build(eq(userTrade)))
                .thenReturn(message);

        SlackMessage build = slackMessageBuilderService.build(userTrade);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals(message, build.getText());
    }

    @Test
    public void testBuild_ask() throws Exception {
        String message = "My Message";
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


        when(messageBodyBuilderService.build(eq(userTrade)))
                .thenReturn(message);


        SlackMessage build = slackMessageBuilderService.build(userTrade);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals(message, build.getText());
    }

    @Test
    public void testBuild() throws Exception {
        String message = "My Message";
        when(messageBodyBuilderService.build(eq(walletComparisonSummary)))
                .thenReturn(message);
        SlackMessage build = slackMessageBuilderService.build(walletComparisonSummary);

        assertNotNull(build);
        assertNotNull(build.getText());
        assertEquals(expectedEmoji, build.getIcon_emoji());
        assertEquals(expectedUsername, build.getUsername());
        assertEquals(message, build.getText());
    }
}
