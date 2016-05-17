package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;
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
    private WalletSummary walletSummary;
    @Mock
    private SlackMessage slackMessage;

    @Test
    public void testNotify2() throws Exception {
        String message = "Message";

        when(slackMessageBuilderService.build(message))
                .thenReturn(slackMessage);

        classUnderTest.notify(message);

        verify(slackMessageBuilderService).build(message);
        verify(slackMessageSenderService).send(slackMessage);
    }

    @Test
    public void testNotify() throws Exception {
        when(slackMessageBuilderService.build(userTrade))
                .thenReturn(slackMessage);

        classUnderTest.notify(userTrade);

        verify(slackMessageBuilderService).build(userTrade);
        verify(slackMessageSenderService).send(slackMessage);
    }

    @Test
    public void testNotify1() throws Exception {

        when(slackMessageBuilderService.build(walletSummary))
                .thenReturn(slackMessage);

        classUnderTest.notify(walletSummary);

        verify(slackMessageBuilderService).build(walletSummary);
        verify(slackMessageSenderService).send(slackMessage);
    }
}
