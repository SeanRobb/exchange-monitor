package work.hoodie.crypto.exchange.monitor.data.common.validator;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import work.hoodie.crypto.exchange.monitor.data.common.domain.DatabaseType;
import work.hoodie.crypto.exchange.monitor.data.mongo.validator.MongoConnectionValidator;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoConnectionValidatorTest {

    @InjectMocks
    private MongoConnectionValidator mongoConnectionValidator;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void isConnected_ReturnsTrue() throws Exception {

        when(mongoTemplate.getCollectionNames())
                .thenReturn(new HashSet<String>());

        boolean connected = mongoConnectionValidator.isConnected();

        assertTrue(connected);

    }

    @Test
    public void isConnected_ReturnsFalse() throws Exception {

        when(mongoTemplate.getCollectionNames())
                .thenThrow(new RuntimeException("Failed"));

        boolean connected = mongoConnectionValidator.isConnected();

        assertFalse(connected);

    }

    @Test
    public void getDatabaseType() throws Exception {

        DatabaseType databaseType = mongoConnectionValidator.getDatabaseType();

        assertEquals(DatabaseType.MONGO, databaseType);
    }
}
