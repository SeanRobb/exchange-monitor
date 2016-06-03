package work.hoodie.exchange.monitor.data.validator;

import work.hoodie.exchange.monitor.data.domain.DatabaseType;

/**
 * Created by sean on 6/1/16.
 */
public interface DatabaseConnectionValidator {

    boolean isConnected();

    DatabaseType getDatabaseType();

}
