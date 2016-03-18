package work.hoodie.crypto.exchange.monitor.service.notification.message.sender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.exchange.monitor.domain.SlackMessage;


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
