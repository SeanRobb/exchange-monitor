package work.hoodie.crypto.exchange.monitor.config;

import com.xeiam.xchange.ExchangeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.hoodie.crypto.exchange.monitor.service.ExchangeFinderService;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ExchangeConfig {

    @Value("${api.key}")
    private String apiKey;
    @Value("${secret.key}")
    private String secretKey;
    @Value("${exchange}")
    private String exchange;

    private ExchangeFinderService exchangeFinderService = new ExchangeFinderService();

    @PostConstruct
    public void init() {
        log.info("--------- Exchange Configuration ---------");
        log.info("Exchange: " + exchange);
        log.info("Api Key: " + apiKey);
        log.info("Secret Key: " + secretKey);
        log.info("------------------------------------------");
    }

    @Bean
    public ExchangeSpecification exchangeSpecification() {
        Class exchangeClass = exchangeFinderService.find(exchange);

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(exchangeClass);
        exchangeSpecification.setApiKey(apiKey);
        exchangeSpecification.setSecretKey(secretKey);
        return exchangeSpecification;
    }
}
