package work.hoodie.crypto.poloniex.monitor.config;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.poloniex.monitor.service.ExchangeFinderService;
import work.hoodie.crypto.poloniex.monitor.service.SlackNotifierService;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@Import(ExchangeConfig.class)
public class MonitorConfig {
    @Autowired
    private ExchangeSpecification exchangeSpecification;

    @Value("${slack.url}")
    private String slackUrl;

    @PostConstruct
    public void init() {
        log.info("------- Notification Configuration -------");
        log.info("Slack Url: " + slackUrl);
        log.info("------------------------------------------");
    }

    @Bean
    public PollingTradeService pollingTradeService() {
        return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification).getPollingTradeService();
    }

    @Bean
    public SlackNotifierService slackNotifierService() {
        return new SlackNotifierService(slackUrl, new RestTemplate());
    }
}
