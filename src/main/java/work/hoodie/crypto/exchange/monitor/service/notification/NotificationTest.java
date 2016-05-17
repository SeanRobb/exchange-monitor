package work.hoodie.crypto.exchange.monitor.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.service.notification.service.NotifierService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class NotificationTest {

    private static final String TEST_MESSAGE = "This is a Test Notification";
    @Value("${notification.test:false}")
    private Boolean testNotification;


    @Autowired
    @Qualifier("CorrectNotifierService")
    private NotifierService notifierService;

    @PostConstruct
    public void test() {
        if (testNotification) {
            log.info("Sending Test Notification...");
            notifierService.notify(TEST_MESSAGE);
        }
    }
}
