package work.hoodie.crypto.exchange.monitor.data.mongo.config;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import work.hoodie.crypto.exchange.monitor.data.mongo.validator.MongoConnectionValidator;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@EnableMongoRepositories("work.hoodie.crypto.exchange.monitor.data.mongo.repository")
public class MongoConfig {

    public static final String EXCHANGE_MONITOR_DB = "exchangeMonitorDb";

    @Value("${mongo.database.host:mongo}")
    private String mongoHost;

    @Value("${mongo.database.port:27017}")
    private Integer mongoPort;

    @Autowired
    private MongoConnectionValidator mongoConnectionValidator;

    @PostConstruct
    public void init() {
        log.info("---------- Mongo Configuration -----------");
        log.info("Mongo Host: " + mongoHost);
        log.info("Mongo Port: " + mongoPort);
        log.info("Mongo Connected: " + mongoConnectionValidator.isConnected());
        log.info("------------------------------------------");
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongoHost, mongoPort), EXCHANGE_MONITOR_DB);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());

    }

}
