package work.hoodie.crypto.exchange.monitor;

import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparisonSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;
import work.hoodie.crypto.exchange.monitor.service.trade.recent.RecentTradesService;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletComparisonSummaryRetrieverService;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ExchangeMonitor {

    @Autowired
    @Qualifier("CorrectRecentTradeService")
    private RecentTradesService recentTradesService;

    @Autowired
    private WalletComparisonSummaryRetrieverService walletComparisonSummaryRetrieverService;

    @Autowired
    @Qualifier("CorrectNotifierService")
    private NotifierService notifierService;

    @Scheduled(cron = "${monitor.interval:0 1/1 * * * *}")
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

    @Scheduled(cron = "${summary.interval:0 30 7 * * MON}")
    public void summary() {
        try {
            log.info("Building wallet summary...");
            WalletComparisonSummary walletComparisonSummary = walletComparisonSummaryRetrieverService.getWalletSummary();
            notifierService.notify(walletComparisonSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
