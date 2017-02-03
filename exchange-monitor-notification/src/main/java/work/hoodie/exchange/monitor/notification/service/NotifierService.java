package work.hoodie.exchange.monitor.notification.service;

import com.xeiam.xchange.dto.trade.UserTrade;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;

import java.util.List;


public interface NotifierService {
    void notify(String message);

    void notify(List<UserTrade> userTrade);

    void notify(WalletComparisonSummary walletComparisonSummary);
}
