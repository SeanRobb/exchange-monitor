import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletComparison;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.notification.builder.MessageBodyBuilderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MessageBodyBuilderServiceTest {

    @InjectMocks
    private MessageBodyBuilderService classUnderTest;

    @Test
    public void testBuild() throws Exception {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        String message = classUnderTest.build(userTrade);

        assertNotNull(message);
    }

    @Test
    public void testBuild2() throws Exception {
        UserTrade userTrade1 = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        UserTrade userTrade2 = new UserTrade.Builder()
                .type(Order.OrderType.ASK)
                .tradableAmount(BigDecimal.ONE)
                .currencyPair(CurrencyPair.BTC_USD)

                .price(BigDecimal.TEN)
                .timestamp(new Date())
                .id("3")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("USD")
                .orderId("2")
                .build();

        String message = classUnderTest.build(asList(userTrade1, userTrade2));

        assertNotNull(message);
    }

    @Test
    public void testBuild1() throws Exception {
        String currency = "DGB";
        List<WalletComparison> walletComparisons = new ArrayList<WalletComparison>();
        WalletComparison walletComparison = new WalletComparison();
        WalletSummary newBalance = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency)
                                .setAvailable(BigDecimal.ZERO)
                                .setOnOrder(BigDecimal.ONE))
                .setBtcValue(BigDecimal.ONE)
                .setLastPrice(BigDecimal.ZERO);
        WalletSummary oldBalance = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency)
                                .setOnOrder(BigDecimal.ZERO)
                                .setAvailable(BigDecimal.ZERO))
                .setLastPrice(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.ZERO);
        walletComparison.setCurrency(currency);
        walletComparison.setBtcValueGain(BigDecimal.ONE);
        walletComparison.setBalanceGain(BigDecimal.ONE);
        walletComparison.setNewSummary(newBalance);
        walletComparison.setOldSummary(oldBalance);
        walletComparisons.add(walletComparison);
        WalletComparisonSummary walletComparisonSummary = new WalletComparisonSummary();
        walletComparisonSummary.setBtcTotalChange(BigDecimal.ZERO);
        walletComparisonSummary.setWalletComparisons(walletComparisons);

        String message = classUnderTest.build(walletComparisonSummary);
        log.info("\n" + message);
        assertNotNull(message);
    }
}
