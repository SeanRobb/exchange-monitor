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

        String usd = classUnderTest.getFormattedPriceString(new BigDecimal(100), "USD");

        assertEquals("100.00 USD", usd);

    }

    @Test
    public void getFormattedPriceString_USDT() throws Exception {

        String usdt = classUnderTest.getFormattedPriceString(new BigDecimal(0.1), "USDT");

        assertEquals("0.10 USDT", usdt);

    }

    @Test
    public void getFormattedPriceString_Not_USD() throws Exception {

        String abc = classUnderTest.getFormattedPriceString(new BigDecimal(100.00000100), "ABC");

        assertEquals("100.00000100 ABC", abc);
    }
    @Test
    public void getFormatterPriceString_Null() throws Exception {

        BigDecimal price = new BigDecimal(100.00000100);
        String abc = classUnderTest.getFormattedPriceString(price, null);
        assertEquals(price.toString(), abc);
    }
    @Test
    public void getFormatterPriceString_Null_PriceAndCurrency() throws Exception {

        String actual = classUnderTest.getFormattedPriceString(null, null);
        assertEquals("0", actual);
    }
}
