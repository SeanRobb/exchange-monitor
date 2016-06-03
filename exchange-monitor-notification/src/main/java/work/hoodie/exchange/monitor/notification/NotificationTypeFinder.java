package work.hoodie.exchange.monitor.notification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.NotificationType;

@Component
public class NotificationTypeFinder {

    @Value("${slack.url:}")
    private String slackUrl;

    @Value("${email.address:}")
    private String emailAddress;

    @Value("${email.server.host:}")
    private String mailHost;
    @Value("${email.server.username:}")
    private String mailUsername;
    @Value("${email.server.password:}")
    private String mailPassword;

    public NotificationType find() {
        if (isValidSlack()) {
            return NotificationType.SLACK;
        } else if (isValidEmail()) {
            return NotificationType.EMAIL;
        } else if (isValidNone()) {
            return NotificationType.NONE;
        } else {
            throw new RuntimeException("Is not supported notification type");
        }

    }

    private Boolean isValidEmail() {
        return StringUtils.isBlank(slackUrl) && StringUtils.isNotBlank(emailAddress) &&
                StringUtils.isNotBlank(mailHost) && StringUtils.isNotBlank(mailUsername) &&
                StringUtils.isNotBlank(mailPassword) && StringUtils.isNotBlank(emailAddress);
    }

    private Boolean isValidSlack() {
        return StringUtils.isNotBlank(slackUrl) && StringUtils.isBlank(emailAddress) &&
                StringUtils.isBlank(mailHost) && StringUtils.isBlank(mailUsername) &&
                StringUtils.isBlank(mailPassword) && StringUtils.isBlank(emailAddress);
    }

    private Boolean isValidNone() {
        return StringUtils.isBlank(slackUrl) && StringUtils.isBlank(emailAddress) &&
                StringUtils.isBlank(mailHost) && StringUtils.isBlank(mailUsername) &&
                StringUtils.isBlank(mailPassword) && StringUtils.isBlank(emailAddress);
    }
}
