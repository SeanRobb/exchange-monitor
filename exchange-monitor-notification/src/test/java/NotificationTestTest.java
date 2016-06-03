import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.exchange.monitor.notification.NotificationTest;
import work.hoodie.exchange.monitor.notification.service.NotifierService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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
        verify(notifierService,times(1)).notify(anyString());
    }


    @Test
    public void testTestFalse() throws Exception {
        ReflectionTestUtils.setField(classUnderTest,"testNotification",false);
        classUnderTest.test();
        verify(notifierService,times(0)).notify(anyString());
    }
}
