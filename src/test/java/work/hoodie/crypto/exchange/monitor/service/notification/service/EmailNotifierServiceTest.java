package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.EmailMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.EmailMessageSenderService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailNotifierServiceTest {
    @InjectMocks
    private EmailNotifierService classUnderTest;

    @Mock
    private EmailMessageBuilderService emailMessageBuilderService;

    @Mock
    private EmailMessageSenderService emailMessageSenderService;
    @Mock
    private UserTrade userTrade;
    @Mock
    private WalletComparisonSummary walletComparisonSummary;
    @Mock
    private EmailMessage emailMessage;
    @Test
    public void testNotify3() throws Exception {
        String message = "My Message";
        when(emailMessageBuilderService.build(message))
                .thenReturn(emailMessage);
        classUnderTest.notify(message);
        verify(emailMessageBuilderService).build(message);
        verify(emailMessageSenderService).send(emailMessage);
    }


    @Test
    public void testNotify() throws Exception {
        when(emailMessageBuilderService.build(userTrade))
                .thenReturn(emailMessage);

        classUnderTest.notify(userTrade);

        verify(emailMessageBuilderService).build(userTrade);
        verify(emailMessageSenderService).send(emailMessage);
    }

    @Test
    public void testNotify1() throws Exception {
        when(emailMessageBuilderService.build(walletComparisonSummary))
                .thenReturn(emailMessage);
        classUnderTest.notify(walletComparisonSummary);
        verify(emailMessageBuilderService).build(walletComparisonSummary);
        verify(emailMessageSenderService).send(emailMessage);
    }
}
