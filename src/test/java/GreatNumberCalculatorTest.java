import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.service.GreatNumberCalculator;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ryantodd on 3/8/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GreatNumberCalculatorTest {
    @InjectMocks
    private GreatNumberCalculator greatNumberCalculator;

    @Test
    public void getTotal_CalculatesTotalForSingleCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(1))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(400), total);
    }
    @Test
    public void getTotal_CalculatesTotalForDeuceCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(2))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(800), total);
    }
    @Test
    public void getTotal_CalculatesTotalForHalfCoin() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(.5))
                .build();

        BigDecimal total = greatNumberCalculator.getTotal(userTrade);
        assertNotNull(total);
        assertEquals(BigDecimal.valueOf(200).doubleValue(), total.doubleValue(),0.001);
    }
    @Test
    public void getActual_ActualAmountOfCoins() {
        UserTrade userTrade = new UserTrade.Builder()
                .price(BigDecimal.valueOf(400))
                .feeAmount(BigDecimal.valueOf(100))
                .tradableAmount(BigDecimal.valueOf(1))
                .build();


    }
}
