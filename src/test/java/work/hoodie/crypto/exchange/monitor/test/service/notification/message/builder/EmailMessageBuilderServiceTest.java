package work.hoodie.crypto.exchange.monitor.test.service.notification.message.builder;


import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.EmailMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.MessageBodyBuilderService;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailMessageBuilderServiceTest {
    @InjectMocks
    private EmailMessageBuilderService classUnderTest;
    @Mock
    private ExchangeSpecification exchangeSpecification;
    @Mock
    private MessageBodyBuilderService messageBodyBuilderService;

    private final String expectedToEmailAddress = ":moneybag:";
    private final String expectedSubject = "Poloniex Monitor";

    @Before
    public void init() {
        when(exchangeSpecification.getExchangeName()).thenReturn("Poloniex");
        ReflectionTestUtils.setField(classUnderTest, "toEmailAddress", expectedToEmailAddress);
    }

    @Test
    public void testBuild_bid() throws Exception {
        String expectedMessage = "My Message";
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

        when(messageBodyBuilderService.build(eq(userTrade)))
                .thenReturn(expectedMessage);

        EmailMessage emailMessage = classUnderTest.build(userTrade);

        assertNotNull(emailMessage);
        assertEquals(expectedToEmailAddress, emailMessage.getToEmailAddress());
        assertEquals(expectedSubject, emailMessage.getSubject());
        assertEquals(classUnderTest.fromEmailAddress, emailMessage.getFromEmailAddress());
        assertEquals(expectedMessage, emailMessage.getContent());
    }

    @Test
    public void testBuild_ask() throws Exception {
        String expectedMessage = "My Message";
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
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
                .thenReturn(expectedMessage);


        EmailMessage emailMessage = classUnderTest.build(userTrade);

        assertNotNull(emailMessage);
        assertEquals(expectedToEmailAddress, emailMessage.getToEmailAddress());
        assertEquals(expectedSubject, emailMessage.getSubject());
        assertEquals(classUnderTest.fromEmailAddress, emailMessage.getFromEmailAddress());
        assertEquals(expectedMessage, emailMessage.getContent());
    }
}
