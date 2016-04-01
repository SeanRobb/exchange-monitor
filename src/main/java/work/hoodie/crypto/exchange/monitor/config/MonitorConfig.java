package work.hoodie.crypto.exchange.monitor.config;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
