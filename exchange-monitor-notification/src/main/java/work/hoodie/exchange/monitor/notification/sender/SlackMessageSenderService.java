package work.hoodie.exchange.monitor.notification.sender;


import org.springframework.web.client.RestTemplate;
import work.hoodie.exchange.monitor.common.SlackMessage;


public class SlackMessageSenderService {
    private RestTemplate restTemplate;
    private String url;


    public SlackMessageSenderService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public void send(SlackMessage slackMessage) {
        restTemplate.postForObject(url, slackMessage, String.class);
    }


}
