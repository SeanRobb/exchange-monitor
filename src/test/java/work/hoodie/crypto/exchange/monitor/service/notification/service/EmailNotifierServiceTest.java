package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.EmailMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.EmailMessageSenderService;
import work.hoodie.crypto.exchange.monitor.service.notification.service.EmailNotifierService;

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
    private EmailMessage emailMessage;


    @Test
    public void testNotify() throws Exception {
        when(emailMessageBuilderService.build(userTrade))
                .thenReturn(emailMessage);

        classUnderTest.notify(userTrade);

        verify(emailMessageBuilderService).build(userTrade);
        verify(emailMessageSenderService).send(emailMessage);
    }
}
