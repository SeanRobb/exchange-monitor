package work.hoodie.crypto.exchange.monitor.data.common.validator;

import work.hoodie.crypto.exchange.monitor.data.common.domain.DatabaseType;

/**
 * Created by sean on 6/1/16.
 */
public interface DatabaseConnectionValidator {

    boolean isConnected();

    DatabaseType getDatabaseType();

}
