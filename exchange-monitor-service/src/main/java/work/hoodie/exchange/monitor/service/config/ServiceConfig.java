package work.hoodie.exchange.monitor.service.config;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.service.ExchangeFinderService;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration("Service")
@ComponentScan("work.hoodie.exchange.monitor.service")
public class ServiceConfig {

    @Value("${api.key}")
    private String apiKey;
    @Value("${secret.key}")
    private String secretKey;
    @Value("${exchange}")
    private String exchange;

    @Autowired
    private ExchangeFinderService exchangeFinderService;

    @PostConstruct
    public void init() {
        log.info("--------- Exchange Configuration ---------");
        log.info("Exchange: " + exchange);
        log.info("Api Key: " + apiKey);
        log.info("Secret Key: " + secretKey);
        log.info("------------------------------------------");
    }


    @Bean
    public Exchange exchange(){
        Class exchangeClass = exchangeFinderService.find(exchange);
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(exchangeClass);
        exchangeSpecification.setApiKey(apiKey);
        exchangeSpecification.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    }


}
