package work.hoodie.crypto.exchange.monitor.service.recent.trade;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Data
public class TimeRetrieveService {
    private Date queryTime;
    private Date summaryTime;

    @PostConstruct
    public void init() {
        syncQueryTime();
        syncSummaryTime();
    }

    public void syncQueryTime() {
        queryTime = DateTime.now().toDate();
    }

    public void syncSummaryTime() {
        summaryTime = DateTime.now().toDate();
    }


    public Date getAndSyncQueryTime() {
        Date queryTime = getQueryTime();
        syncQueryTime();
        return queryTime;
    }

    public Date getAndSyncSummaryTime() {
        Date summaryTime = getSummaryTime();
        syncSummaryTime();
        return summaryTime;
    }


}
