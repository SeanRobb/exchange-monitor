package work.hoodie.exchange.monitor.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;

public interface NotifierService {
    void notify(String message);

    void notify(UserTrade userTrade);

    void notify(WalletComparisonSummary walletComparisonSummary);
}
