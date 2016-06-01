package work.hoodie.crypto.exchange.monitor.service.wallet;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletComparisonServiceTest {

    @InjectMocks
    private WalletComparisonService classUnderTest;

    @Test
    public void testBuild() throws Exception {
        WalletSummary oldBalance = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency("SomeCoinage")
                                .setAvailable(BigDecimal.valueOf(2))
                                .setOnOrder(BigDecimal.valueOf(7)))
                .setBtcValue(BigDecimal.valueOf(4));
        WalletSummary newBalance = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency("SomeCoinage")
                                .setAvailable(BigDecimal.valueOf(3))
                                .setOnOrder(BigDecimal.valueOf(4)))
                .setBtcValue(BigDecimal.valueOf(9));

        WalletComparison expected = new WalletComparison();
        expected.setCurrency("SomeCoinage");
        expected.setBalanceGain(BigDecimal.valueOf(-2));
        expected.setBtcValueGain(BigDecimal.valueOf(5));
        expected.setNewSummary(newBalance);
        expected.setOldSummary(oldBalance);

        WalletComparison actual = classUnderTest.compare(oldBalance, newBalance);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindMatchingWalletCurrency() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        List<WalletSummary> walletSummaryList = new ArrayList<WalletSummary>();
        WalletSummary expected = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2)))
                .setBtcValue(BigDecimal.valueOf(4));
        walletSummaryList.add(expected);
        walletSummaryList.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency2)
                                .setAvailable(BigDecimal.valueOf(4)))
                .setBtcValue(BigDecimal.valueOf(1)));
        WalletSummary walletSummary = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2)))
                .setBtcValue(BigDecimal.valueOf(6));

        WalletSummary actual = classUnderTest.findMatchingWalletCurrency(walletSummaryList, walletSummary);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindMatchingWalletCurrency_NoMatch() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        String currency3 = "SpecialCoin";
        List<WalletSummary> walletSummaryList = new ArrayList<WalletSummary>();
        WalletSummary notExpected1 = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2)))
                .setBtcValue(BigDecimal.valueOf(4));
        WalletSummary notExpected2 = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency2)
                                .setAvailable(BigDecimal.valueOf(4)))
                .setBtcValue(BigDecimal.valueOf(1));
        walletSummaryList.add(notExpected1);
        walletSummaryList.add(notExpected2);
        WalletSummary walletSummary = new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency3)
                                .setAvailable(BigDecimal.valueOf(2)))
                .setBtcValue(BigDecimal.valueOf(6));

        WalletSummary actual = classUnderTest.findMatchingWalletCurrency(walletSummaryList, walletSummary);

        assertNotNull(actual);
        assertNotEquals(notExpected1, actual);
        assertNotEquals(notExpected2, actual);
        assertEquals(BigDecimal.ZERO, actual.getAvailable());
        assertEquals(BigDecimal.ZERO, actual.getBtcValue());
        assertEquals(currency3, actual.getCurrency());
    }
}
