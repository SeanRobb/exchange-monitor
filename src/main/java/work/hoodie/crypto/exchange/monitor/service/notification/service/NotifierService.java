package work.hoodie.crypto.exchange.monitor.service.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

/**
 * Created by sean on 3/18/16.
 */
public interface NotifierService {
    void notify(UserTrade userTrade);

    void notify(WalletSummary walletSummary);
}
