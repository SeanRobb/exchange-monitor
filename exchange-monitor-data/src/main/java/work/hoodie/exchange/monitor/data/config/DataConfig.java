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
import java.net.UnknownHostException;

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
    private MongoClient mongoClient;
    private SimpleMongoDbFactory simpleMongoDbFactory;

    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        log.info("Mongo Client Info - Host: {} Port: {}", mongoHost, mongoPort);
        if (mongoClient == null) {
            mongoClient = new MongoClient(mongoHost, mongoPort);
        }
        if (simpleMongoDbFactory == null) {
            simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, EXCHANGE_MONITOR_DB);
        }
        return simpleMongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        if (this.mongoTemplate == null) {
            mongoTemplate = new MongoTemplate(mongoDbFactory());
        }
        return mongoTemplate;
    }

    @PostConstruct
    public void init() throws UnknownHostException {
        boolean connected = MongoConnectionValidator.isConnected(mongoTemplate());
        if (connected) {
            log.info("---------- Mongo Configuration -----------");
            log.info("Mongo Host: " + mongoHost);
            log.info("Mongo Port: " + mongoPort);
            log.info("Mongo Connected: " + connected);
            log.info("------------------------------------------");
        } else {
            log.info("No Database Connected...");
        }
    }

}
