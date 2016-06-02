package work.hoodie.crypto.exchange.monitor.data.common.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.data.mongo.dao.BalanceSnapshotMongoDao;
import work.hoodie.crypto.exchange.monitor.data.mongo.repository.BalanceSnapshotMongoRepository;
import work.hoodie.crypto.exchange.monitor.domain.BalanceSnapshot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceSnapshotMongoDaoTest {
    @InjectMocks
    private BalanceSnapshotMongoDao classUnderTest;

    @Mock
    private BalanceSnapshotMongoRepository balanceSnapshotMongoRepository;

    @Mock
    private BalanceSnapshot balanceSnapshotSent;
    @Mock
    private BalanceSnapshot balanceSnapshotReturn;

    @Test
    public void save() throws Exception {
        when(balanceSnapshotMongoRepository.save(balanceSnapshotSent))
                .thenReturn(balanceSnapshotReturn);

        BalanceSnapshot save = classUnderTest.save(balanceSnapshotSent);

        assertNotNull(save);
        verify(balanceSnapshotMongoRepository).save(balanceSnapshotSent);
        assertEquals(balanceSnapshotReturn, save);
    }

    @Test
    public void delete() throws Exception {
        String id = "my Id";
        classUnderTest.delete(id);
        verify(balanceSnapshotMongoRepository).delete(eq(id));
    }


}
