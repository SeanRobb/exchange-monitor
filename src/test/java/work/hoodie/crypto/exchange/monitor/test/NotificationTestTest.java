package work.hoodie.crypto.exchange.monitor.test;

import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.NotificationTest;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NotificationTestTest {

    @InjectMocks
    private NotificationTest classUnderTest;

    @Mock
    private NotifierService notifierService;

    @Test
    public void testTestTrue() throws Exception {
        ReflectionTestUtils.setField(classUnderTest,"testNotification",true);
        classUnderTest.test();
        verify(notifierService,times(1)).notify(any(UserTrade.class));
    }


    @Test
    public void testTestFalse() throws Exception {
        ReflectionTestUtils.setField(classUnderTest,"testNotification",false);
        classUnderTest.test();
        verify(notifierService,times(0)).notify(any(UserTrade.class));
    }
}
