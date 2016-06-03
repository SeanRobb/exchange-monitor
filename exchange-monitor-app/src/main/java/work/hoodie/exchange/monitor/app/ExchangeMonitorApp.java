package work.hoodie.exchange.monitor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import work.hoodie.exchange.monitor.app.config.ExchangeMonitorConfig;

@SpringBootApplication
@Configuration
@Import(ExchangeMonitorConfig.class)
public class ExchangeMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(ExchangeMonitorApp.class, args);
    }
}
