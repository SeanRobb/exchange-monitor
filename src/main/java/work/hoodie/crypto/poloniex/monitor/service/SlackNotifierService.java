package work.hoodie.crypto.poloniex.monitor.service;


import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.poloniex.monitor.domain.SlackMessage;

public class SlackNotifierService {
    private RestTemplate restTemplate;
    private String url;

    public SlackNotifierService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    public void notify(SlackMessage message) {
        restTemplate.postForObject(url, message, String.class);
    }
}
