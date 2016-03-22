package work.hoodie.crypto.exchange.monitor.test.service.notification.message.sender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.SlackMessageSenderService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlackMessageSenderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testNotify() throws Exception {
        String message = "AbstractMessage";
        String url = "Url";
        SlackMessage value = new SlackMessage().setText(message);

        SlackMessageSenderService slackNotifierService = new SlackMessageSenderService(url, restTemplate);

        slackNotifierService.send(value);

        verify(restTemplate).postForObject(eq(url), eq(value), eq(String.class));
    }
}
