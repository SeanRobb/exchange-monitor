import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.SlackNotifierService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlackNotifierServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testNotify() throws Exception {
        String message = "Message";
        String url = "Url";
        SlackMessage value = new SlackMessage().setText(message);

        SlackNotifierService slackNotifierService = new SlackNotifierService(url, restTemplate);

        slackNotifierService.notify(value);

        verify(restTemplate).postForObject(eq(url), eq(value), eq(String.class));
    }
}
