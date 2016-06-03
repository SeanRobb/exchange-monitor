package work.hoodie.exchange.monitor.data.config;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import work.hoodie.exchange.monitor.data.validator.MongoConnectionValidator;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration("Database")
@ComponentScan("work.hoodie.exchange.monitor.data")
@EnableMongoRepositories("work.hoodie.exchange.monitor.data.repository")
public class DataConfig {

    public static final String EXCHANGE_MONITOR_DB = "exchangeMonitorDb";

    @Value("${mongo.database.host:mongo}")
    private String mongoHost;

    @Value("${mongo.database.port:27017}")
    private Integer mongoPort;

    public MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        log.info("---------- Mongo Configuration -----------");
        log.info("Mongo Host: " + mongoHost);
        log.info("Mongo Port: " + mongoPort);
        log.info("Mongo Connected: " + MongoConnectionValidator.isConnected(mongoTemplate));
        log.info("------------------------------------------");
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongoHost, mongoPort), EXCHANGE_MONITOR_DB);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        this.mongoTemplate = mongoTemplate;
        return mongoTemplate;
    }

}
