package work.hoodie.crypto.exchange.monitor;

import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.RecentTradesService;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletSummaryRetrieverService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ExchangeMonitor {

    @Autowired
    @Qualifier("CorrectRecentTradeService")
    private RecentTradesService recentTradesService;

    @Autowired
    private WalletSummaryRetrieverService walletSummaryRetrieverService;

    @Autowired
    @Qualifier("CorrectNotifierService")
    private NotifierService notifierService;


    @Scheduled(cron = "${monitor.interval:0 */1 * * * *}")
    public void check() {
        List<UserTrade> history = recentTradesService.getHistory();

        if (!history.isEmpty())
            log.info(history.toString());
        else
            log.debug("No Trades");

        for (UserTrade trade : history) {
            notifierService.notify(trade);
        }
    }

    @Scheduled(cron = "${summary.interval:0 0 12 1/1 * ?}")
    public void summary() {
        try {
            log.info("Building wallet summary");
            WalletSummary walletSummary = walletSummaryRetrieverService.getWalletSummary();
            notifierService.notify(walletSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
