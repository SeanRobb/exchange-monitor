package work.hoodie.crypto.exchange.monitor.service.trade.recent;

import com.xeiam.xchange.dto.trade.UserTrade;

import java.util.List;

/**
 * Created by sean on 2/27/16.
 */
public interface RecentTradesService {

    List<UserTrade> getHistory();
}
