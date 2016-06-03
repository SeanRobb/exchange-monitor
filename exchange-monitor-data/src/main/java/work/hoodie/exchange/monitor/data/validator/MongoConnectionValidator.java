package work.hoodie.exchange.monitor.data.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.data.domain.DatabaseType;

@Component
public class MongoConnectionValidator implements DatabaseConnectionValidator {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean isConnected() {
        return isConnected(mongoTemplate);
    }

    public DatabaseType getDatabaseType() {
        return DatabaseType.MONGO;
    }

    public static boolean isConnected(MongoTemplate mongoTemplate) {
        try {
            mongoTemplate.getCollectionNames();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
