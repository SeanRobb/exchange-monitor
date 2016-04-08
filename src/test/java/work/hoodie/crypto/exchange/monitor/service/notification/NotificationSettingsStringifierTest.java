package work.hoodie.crypto.exchange.monitor.service.notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.domain.NotificationType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class NotificationSettingsStringifierTest {
    @InjectMocks
    private NotificationSettingsStringifier classUnderTest;

    @Mock
    private NotificationTypeFinder notificationTypeFinder;

    private String emailAddress = "EmailAddress@Email.com";
    private String mailHost = "Hostname";
    private int mailPort = 12;
    private String mailUsername = "UserNAme";
    private String mailPassword = "SecretPassword";
    private String slackUrl = "TopSecretSlackUrl";

    @Test
    public void testGetSettings_Email() {
        ReflectionTestUtils.setField(classUnderTest, "emailAddress", emailAddress);
        ReflectionTestUtils.setField(classUnderTest, "mailHost", mailHost);
        ReflectionTestUtils.setField(classUnderTest, "mailPort", mailPort);
        ReflectionTestUtils.setField(classUnderTest, "mailUsername", mailUsername);
        ReflectionTestUtils.setField(classUnderTest, "mailPassword", mailPassword);

        when(notificationTypeFinder.find()).thenReturn(NotificationType.EMAIL);

        String actualMessage = classUnderTest.getSettings();

        String expectedMessage = "Notification Type: " + NotificationType.EMAIL + "\n" +
                "Email Address: " + emailAddress +
                "SMTP Mail Host: " + mailHost +
                "SMTP Mail Port: " + mailPort +
                "SMTP Mail Username: " + mailUsername +
                "SMTP Mail Password: " + mailPassword;

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    public void testGetSettings_Slack() {
        ReflectionTestUtils.setField(classUnderTest, "slackUrl", slackUrl);

        when(notificationTypeFinder.find()).thenReturn(NotificationType.SLACK);

        String actualMessage = classUnderTest.getSettings();

        String expectedMessage = "Notification Type: " + NotificationType.SLACK + " "  + slackUrl;

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }
}
