package work.hoodie.crypto.exchange.monitor.config;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import work.hoodie.crypto.exchange.monitor.domain.NotificationType;
import work.hoodie.crypto.exchange.monitor.service.notification.NotificationTypeFinder;
import work.hoodie.crypto.exchange.monitor.service.notification.config.NotificationConfig;
import work.hoodie.crypto.exchange.monitor.service.notification.service.EmailNotifierService;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;
import work.hoodie.crypto.exchange.monitor.service.notification.service.SlackNotifierService;
import work.hoodie.crypto.exchange.monitor.service.trade.config.ExchangeConfig;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.RecentTradeServiceFinder;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.RecentTradesService;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration("Monitor")
@Import({ExchangeConfig.class, NotificationConfig.class})
@DependsOn({"Exchange", "Notification"})
public class MonitorConfig {
    @Autowired
    private Exchange exchange;
    @Autowired
    private RecentTradeServiceFinder recentTradeServiceFinder;
    @Autowired
    private SlackNotifierService slackNotifierService;
    @Autowired
    private EmailNotifierService emailNotifierService;

    @Autowired
    private NotificationTypeFinder notificationTypeFinder;
    @Value("${monitor.interval:0 1/1 * * * *}")
    private String monitorInterval;
    @Value("${summary.interval:0 30 7 * * MON}")
    private String summaryInterval;
    @Value("${snapshot.interval:0 0 1/1 * * *}")
    private String snapshotInterval;


    @Bean
    public PollingTradeService pollingTradeService() {
        return exchange.getPollingTradeService();
    }

    @Bean
    public PollingMarketDataService pollingMarketDataService() {
        return exchange.getPollingMarketDataService();
    }

    @Bean
    public PollingAccountService pollingAccountService() {
        return exchange.getPollingAccountService();
    }

    @Bean
    public ExchangeSpecification exchangeSpecification() {
        return exchange.getExchangeSpecification();
    }


    @Bean(name = "CorrectRecentTradeService")
    public RecentTradesService recentTradesService() {
        return recentTradeServiceFinder.find(exchange.getExchangeSpecification());
    }

    @Bean(name = "CorrectNotifierService")
    public NotifierService abstractNotifierService() {
        NotificationType notificationType = notificationTypeFinder.find();
        if (notificationType == NotificationType.EMAIL) {
            return emailNotifierService;
        } else {
            return slackNotifierService;
        }
    }

    @PostConstruct
    public void init() {
        log.info("---------- Monitor Configuration ---------");
        log.info("Monitor Interval Cron: " + monitorInterval);
        log.info("Summary Interval Cron: " + summaryInterval);
        log.info("Snapshot Interval Cron: " + snapshotInterval);
        log.info("------------------------------------------");

    }

}
