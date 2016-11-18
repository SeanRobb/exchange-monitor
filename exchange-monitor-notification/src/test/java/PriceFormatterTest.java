import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.notification.formatter.PriceFormatter;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PriceFormatterTest {

    @InjectMocks
    private PriceFormatter classUnderTest;

    @Test
    public void getFormattedPriceString_USD() throws Exception {

        String usd = classUnderTest.getFormattedPriceString(new BigDecimal(1), "USD");

        assertEquals("1.00", usd);

    }

    @Test
    public void getFormattedPriceString_USDT() throws Exception {

        String usdt = classUnderTest.getFormattedPriceString(new BigDecimal(0.1), "USDT");

        assertEquals("0.10", usdt);

    }

    @Test
    public void getFormattedPriceString_Not_USD() throws Exception {

        String abc = classUnderTest.getFormattedPriceString(new BigDecimal(0.00000100), "ABC");

        assertEquals("0.00000100", abc);
    }
}
