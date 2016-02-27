package work.hoodie.crypto.exchange.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import work.hoodie.crypto.exchange.monitor.config.MonitorConfig;

@SpringBootApplication
@Configuration
@Import(MonitorConfig.class)
public class ExchangeMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(ExchangeMonitorApp.class, args);
    }
}
