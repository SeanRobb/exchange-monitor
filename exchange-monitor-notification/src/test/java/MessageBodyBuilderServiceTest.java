import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletComparison;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.notification.builder.MessageBodyBuilderService;
import work.hoodie.exchange.monitor.notification.formatter.PriceFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MessageBodyBuilderServiceTest {

    @InjectMocks
    private MessageBodyBuilderService classUnderTest;

    @Mock
    private PriceFormatter priceFormatter;

    @Test
    public void testBuild() throws Exception {
        String formattedPrice = "My Amazing Formatted Price" + UUID.randomUUID().toString();
        BigDecimal price = BigDecimal.ONE;
        CurrencyPair currencyPair = CurrencyPair.DOGE_LTC;
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(currencyPair)

                .price(price)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        when(priceFormatter.getFormattedPriceString(price, currencyPair.counterSymbol))
                .thenReturn(formattedPrice);

        String message = classUnderTest.build(userTrade);

        assertNotNull(message);
        verify(priceFormatter).getFormattedPriceString(price, currencyPair.counterSymbol);
        assertThat(message, containsString(formattedPrice));
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
