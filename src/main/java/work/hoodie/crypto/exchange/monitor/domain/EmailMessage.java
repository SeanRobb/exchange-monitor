package work.hoodie.crypto.exchange.monitor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String fromEmailAddress;
    private String toEmailAddress;
    private String subject;
    private String content;
}
