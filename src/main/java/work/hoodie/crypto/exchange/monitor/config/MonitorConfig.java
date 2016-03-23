package work.hoodie.crypto.exchange.monitor.config;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
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
import work.hoodie.crypto.exchange.monitor.service.recent.trade.RecentTradeServiceFinder;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.RecentTradesService;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.config.ExchangeConfig;

@Configuration("Monitor")
@Import({ExchangeConfig.class, NotificationConfig.class})
@DependsOn({"Exchange", "Notification"})
public class MonitorConfig {
    @Autowired
    private ExchangeSpecification exchangeSpecification;
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
        return ExchangeFactory.INSTANCE
                .createExchange(exchangeSpecification)
                .getPollingTradeService();
    }

    @Bean(name = "CorrectRecentTradeService")
    public RecentTradesService recentTradesService() {
        return recentTradeServiceFinder.find(exchangeSpecification);
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
