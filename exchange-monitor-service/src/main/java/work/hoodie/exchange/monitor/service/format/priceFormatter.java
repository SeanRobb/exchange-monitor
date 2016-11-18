package work.hoodie.exchange.monitor.service.format;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class PriceFormatter {

    public String getFormattedPriceString(BigDecimal price, String currency) {
        if(currency.equalsIgnoreCase("USD")||currency.equalsIgnoreCase("USDT")){
            return new DecimalFormat("0.00").format(price);
        }

        return new DecimalFormat("0.00000000").format(price);
    }
}
