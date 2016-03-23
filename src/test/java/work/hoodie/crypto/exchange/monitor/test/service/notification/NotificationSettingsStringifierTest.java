package work.hoodie.crypto.exchange.monitor.test.service.notification;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.service.notification.NotificationSettingsStringifier;

import static org.junit.Assert.assertTrue;

/**
 * Created by sean on 3/22/16.
 */
public class NotificationSettingsStringifierTest {
    @InjectMocks
    private NotificationSettingsStringifier classUnderTest;

    private String emailAddress;
    private String mailHost;
    private int mailPort;
    private String mailUsername;
    private String mailPassword;

    @Test
    public void testGetSettings() {
        ReflectionTestUtils.setField(classUnderTest, "emailAddress", emailAddress);
        ReflectionTestUtils.setField(classUnderTest, "mailHost", mailHost);
        ReflectionTestUtils.setField(classUnderTest, "mailPort", mailPort);
        ReflectionTestUtils.setField(classUnderTest, "mailUsername", mailUsername);
        ReflectionTestUtils.setField(classUnderTest, "mailPassword", mailPassword);

        String settings = classUnderTest.getSettings();


        assertTrue(true);
    }
}
