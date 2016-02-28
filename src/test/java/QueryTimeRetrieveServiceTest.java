import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.recent.trade.QueryTimeRetrieveService;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryTimeRetrieveServiceTest {
    @InjectMocks
    private QueryTimeRetrieveService queryTimeRetrieveService;

    @Test
    public void testInit() throws Exception {
        Date preInit = queryTimeRetrieveService.getQueryTime();
        queryTimeRetrieveService.init();
        Date postInit = queryTimeRetrieveService.getQueryTime();
        assertNull(preInit);
        assertNotNull(postInit);
    }


    @Test
    public void testSyncQueryTime() throws Exception {
        Date preInit = queryTimeRetrieveService.getQueryTime();
        queryTimeRetrieveService.syncQueryTime();
        Date postInit = queryTimeRetrieveService.getQueryTime();
        assertNotEquals(preInit, postInit);
    }


    @Test
    public void testGetAndSyncQueryTime() throws Exception {
        queryTimeRetrieveService.init();
        Thread.sleep(1);
        Date queryTime = queryTimeRetrieveService.getQueryTime();
        Date andSyncQueryTime = queryTimeRetrieveService.getAndSyncQueryTime();
        Date queryTime1 = queryTimeRetrieveService.getQueryTime();
        assertEquals(queryTime, andSyncQueryTime);
        assertNotEquals(queryTime1, andSyncQueryTime);
    }

}
