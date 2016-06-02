package work.hoodie.crypto.exchange.monitor.data.mongo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.data.common.domain.DatabaseType;
import work.hoodie.crypto.exchange.monitor.data.common.validator.DatabaseConnectionValidator;

@Component
public class MongoConnectionValidator implements DatabaseConnectionValidator {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean isConnected() {
        try {
            mongoTemplate.getCollectionNames();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DatabaseType getDatabaseType() {
        return DatabaseType.MONGO;
    }

}
