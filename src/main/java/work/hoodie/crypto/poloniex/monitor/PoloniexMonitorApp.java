package work.hoodie.crypto.poloniex.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import work.hoodie.crypto.poloniex.monitor.config.MonitorConfig;

@SpringBootApplication
@Configuration
@Import(MonitorConfig.class)
public class PoloniexMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(PoloniexMonitorApp.class, args);
    }
}
