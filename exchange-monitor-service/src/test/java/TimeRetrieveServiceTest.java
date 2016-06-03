import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.service.trade.recent.TimeRetrieveService;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TimeRetrieveServiceTest {
    @InjectMocks
    private TimeRetrieveService timeRetrieveService;

    @Test
    public void testInit() throws Exception {
        Date preQueryInit = timeRetrieveService.getQueryTime();
        Date preSummaryInit = timeRetrieveService.getSummaryTime();
        timeRetrieveService.init();
        Date postQueryInit = timeRetrieveService.getQueryTime();
        Date postSummaryInit = timeRetrieveService.getSummaryTime();
        assertNull(preQueryInit);
        assertNotNull(postQueryInit);
        assertNull(preSummaryInit);
        assertNotNull(postSummaryInit);
    }


    @Test
    public void testSyncQueryTime() throws Exception {
        Date preInit = timeRetrieveService.getQueryTime();
        timeRetrieveService.syncQueryTime();
        Date postInit = timeRetrieveService.getQueryTime();
        assertNotEquals(preInit, postInit);
    }


    @Test
    public void testGetAndSyncQueryTime() throws Exception {
        timeRetrieveService.init();
        Thread.sleep(1);
        Date queryTime = timeRetrieveService.getQueryTime();
        Date andSyncQueryTime = timeRetrieveService.getAndSyncQueryTime();
        Date queryTime1 = timeRetrieveService.getQueryTime();
        assertEquals(queryTime, andSyncQueryTime);
        assertNotEquals(queryTime1, andSyncQueryTime);
    }

    @Test
    public void testSyncSummaryTime() throws Exception {
        Date preInit = timeRetrieveService.getSummaryTime();
        timeRetrieveService.syncSummaryTime();
        Date postInit = timeRetrieveService.getSummaryTime();
        assertNotEquals(preInit, postInit);
    }

    @Test
    public void testGetAndSyncSummaryTime() throws Exception {
        timeRetrieveService.init();
        Thread.sleep(1);
        Date summaryTime = timeRetrieveService.getSummaryTime();
        Date andSyncSummaryTime = timeRetrieveService.getAndSyncSummaryTime();
        Date summaryTime1 = timeRetrieveService.getSummaryTime();
        assertEquals(summaryTime, andSyncSummaryTime);
        assertNotEquals(summaryTime1, andSyncSummaryTime);

    }
}
