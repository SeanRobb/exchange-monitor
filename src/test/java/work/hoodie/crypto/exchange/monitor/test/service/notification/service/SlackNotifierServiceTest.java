package work.hoodie.crypto.exchange.monitor.test.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.SlackMessageBuilderService;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.SlackMessageSenderService;
import work.hoodie.crypto.exchange.monitor.service.notification.service.SlackNotifierService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlackNotifierServiceTest {

    @InjectMocks
    private SlackNotifierService classUnderTest;

    @Mock
    private SlackMessageBuilderService slackMessageBuilderService;
    @Mock
    private SlackMessageSenderService slackMessageSenderService;
    @Mock
    private UserTrade userTrade;
    @Mock
    private SlackMessage slackMessage;


    @Test
    public void testNotify() throws Exception {
        when(slackMessageBuilderService.build(userTrade))
                .thenReturn(slackMessage);

        classUnderTest.notify(userTrade);

        verify(slackMessageBuilderService).build(userTrade);
        verify(slackMessageSenderService).send(slackMessage);
    }
}
