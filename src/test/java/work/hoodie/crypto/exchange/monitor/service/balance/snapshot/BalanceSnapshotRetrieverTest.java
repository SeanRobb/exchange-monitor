package work.hoodie.crypto.exchange.monitor.service.balance.snapshot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.BalanceSnapshot;
import work.hoodie.crypto.exchange.monitor.service.balance.BalanceRetrieverService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceSnapshotRetrieverTest {
    @InjectMocks
    private BalanceSnapshotRetriever classUnderTest;
    @Mock
    private BalanceRetrieverService balanceRetrieverService;
    @Mock
    private List<Balance> balances;

    @Test
    public void getCurrentSnapshot() throws Exception {
        String instanceId = "my Amazing Id";

        ReflectionTestUtils.setField(classUnderTest,"instanceId", instanceId);
        when(balanceRetrieverService.getBalances())
                .thenReturn(balances);

        Date before = new Date();
        Thread.sleep(1);

        BalanceSnapshot currentSnapshot = classUnderTest.getCurrentSnapshot();

        Thread.sleep(1);
        Date after = new Date();

        assertNotNull(currentSnapshot);
        assertNotNull(currentSnapshot.getBalances());
        assertNotNull(currentSnapshot.getDate());
        assertNotNull(currentSnapshot.getInstanceId());
        verify(balanceRetrieverService).getBalances();
        assertEquals(balances, currentSnapshot.getBalances());
        assertTrue(currentSnapshot.getDate().after(before));
        assertTrue(currentSnapshot.getDate().before(after));
        assertEquals(instanceId,currentSnapshot.getInstanceId());
    }


}
