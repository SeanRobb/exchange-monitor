package work.hoodie.exchange.monitor.app;

import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.BalanceSnapshot;
import work.hoodie.exchange.monitor.common.RecentUserTrade;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.data.dao.BalanceSnapshotDao;
import work.hoodie.exchange.monitor.data.dao.UserTradeDao;
import work.hoodie.exchange.monitor.data.service.UserTradeService;
import work.hoodie.exchange.monitor.data.validator.DatabaseConnectionValidator;
import work.hoodie.exchange.monitor.notification.service.NotifierService;
import work.hoodie.exchange.monitor.service.balance.snapshot.BalanceSnapshotRetriever;
import work.hoodie.exchange.monitor.service.trade.recent.RecentTradesService;
import work.hoodie.exchange.monitor.service.trade.recent.RecentUserTradeMarshaller;
import work.hoodie.exchange.monitor.service.wallet.WalletComparisonSummaryRetrieverService;

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
    private BalanceSnapshotRetriever balanceSnapshotRetriever;

    @Autowired
    @Qualifier("CorrectNotifierService")

    private NotifierService notifierService;
    private UserTradeService userTradeService;

    @Autowired
    private BalanceSnapshotDao balanceSnapshotDao;

    @Autowired
    private UserTradeDao userTradeDao;

    @Autowired
    private DatabaseConnectionValidator databaseConnectionValidator;

    @Autowired
    private RecentUserTradeMarshaller recentUserTradeMarshaller;

    @Scheduled(cron = "${monitor.interval:0 1/1 * * * *}")
    public void check() {
        List<UserTrade> history = recentTradesService.getHistory();
        List<RecentUserTrade> recentTradeList = recentUserTradeMarshaller.convert(history);


        if (!history.isEmpty())
            log.info(history.toString());
        else
            log.debug("No Trades");

        if (!history.isEmpty())
            notifierService.notify(history);
        if (!history.isEmpty() && databaseConnectionValidator.isConnected()) {
            log.info("Building trade for mongo...");

            userTradeService.save(recentTradeList);
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

    @Scheduled(cron = "${snapshot.interval:0 0 0 1/1 * *}")
    public void snapshot() {
        try {
            if (databaseConnectionValidator.isConnected()) {
                log.info("Building snapshot...");
                BalanceSnapshot currentSnapshot = balanceSnapshotRetriever.getCurrentSnapshot();
                balanceSnapshotDao.save(currentSnapshot);
            } else {
                log.debug("No snapshot because database connected...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
