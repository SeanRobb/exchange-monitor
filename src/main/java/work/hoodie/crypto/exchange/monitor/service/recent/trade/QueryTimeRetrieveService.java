package work.hoodie.crypto.exchange.monitor.service.recent.trade;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class QueryTimeRetrieveService {
    private Date queryTime;

    @PostConstruct
    public void init() {
        syncQueryTime();
    }

    public void syncQueryTime() {
        queryTime = DateTime.now().toDate();
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public Date getAndSyncQueryTime(){
        Date queryTime = getQueryTime();
        syncQueryTime();
        return queryTime;
    }

}
