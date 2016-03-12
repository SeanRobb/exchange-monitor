package work.hoodie.crypto.exchange.monitor.domain;

import lombok.Data;

@Data
public class EmailMessage {
    private String fromEmailAddress;
    private String toEmailAddress;
    private String subject;
    private String content;
}
