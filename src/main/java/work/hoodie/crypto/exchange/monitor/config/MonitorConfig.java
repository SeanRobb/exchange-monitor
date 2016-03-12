package work.hoodie.crypto.exchange.monitor.config;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import work.hoodie.crypto.exchange.monitor.service.notification.SlackNotifierService;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.RecentTradeServiceFinder;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.RecentTradesService;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Configuration
@Slf4j
@Import(ExchangeConfig.class)
@DependsOn("Exchange")
public class MonitorConfig {
    @Autowired
    private ExchangeSpecification exchangeSpecification;
    @Autowired
    private RecentTradeServiceFinder recentTradeServiceFinder;

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
        return ExchangeFactory.INSTANCE
                .createExchange(exchangeSpecification)
                .getPollingTradeService();
    }

    @Bean(name = "CorrectRecentTradeService")
    public RecentTradesService recentTradesService() {
        return recentTradeServiceFinder.find(exchangeSpecification);
    }

    @Bean
    public SlackNotifierService slackNotifierService() {
        return new SlackNotifierService(slackUrl, new RestTemplate());
    }

}
