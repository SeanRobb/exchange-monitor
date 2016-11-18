package work.hoodie.exchange.monitor.notification.formatter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class PriceFormatter {

    public String getFormattedPriceString(BigDecimal price, String currency) {
        String pattern;

        if (currency.equalsIgnoreCase("USD") || currency.equalsIgnoreCase("USDT")) {
            pattern = "0.00";
        } else {
            pattern = "0.00000000";
        }

        return new DecimalFormat(pattern).format(price) + " " + currency;
    }
}
