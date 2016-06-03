import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletComparisonSummary;
import work.hoodie.exchange.monitor.common.WalletSummary;
import work.hoodie.exchange.monitor.service.wallet.WalletComparisonService;
import work.hoodie.exchange.monitor.service.wallet.WalletComparisonSummaryRetrieverService;
import work.hoodie.exchange.monitor.service.wallet.WalletSummaryRetrieverService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletComparisonSummaryRetrieverServiceTest {

    @InjectMocks
    private WalletComparisonSummaryRetrieverService classUnderTest;

    @Mock
    private WalletComparisonService walletComparisonService;
    @Mock
    private WalletSummaryRetrieverService walletSummaryRetrieverService;
    @Mock
    private List<WalletSummary> walletSummaryMock1;
    @Mock
    private List<WalletSummary> walletSummaryMock2;
    @Mock
    private List<WalletSummary> walletSummaryMock3;


    @Test
    public void testGetNewWallets() throws Exception {
        classUnderTest.retrieveNewWallets();

        verify(walletSummaryRetrieverService).getWalletSummaries();
    }

    @Test
    public void testSync() throws Exception {
        ReflectionTestUtils.setField(classUnderTest, "oldWalletSummaries", walletSummaryMock1);
        ReflectionTestUtils.setField(classUnderTest, "newWalletSummaries", walletSummaryMock2);

        when(walletSummaryRetrieverService.getWalletSummaries())
                .thenReturn(walletSummaryMock3);

        classUnderTest.sync();


        List<WalletSummary> oldWalletSummaries = (List<WalletSummary>) ReflectionTestUtils
                .getField(classUnderTest, "oldWalletSummaries");
        List<WalletSummary> newWalletSummaries = (List<WalletSummary>) ReflectionTestUtils
                .getField(classUnderTest, "newWalletSummaries");

        assertEquals(oldWalletSummaries, walletSummaryMock2);
        verify(walletSummaryRetrieverService).getWalletSummaries();
        assertEquals(newWalletSummaries, walletSummaryMock3);
    }

    @Test
    public void testGetWalletComparisons() throws Exception {
        String currency1 = "DOGE";
        List<WalletSummary> walletSummary1 = new ArrayList<WalletSummary>();
        walletSummary1.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(4)));
        List<WalletSummary> walletSummary2 = new ArrayList<WalletSummary>();
        walletSummary2.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(6)));
        ReflectionTestUtils.setField(classUnderTest, "newWalletSummaries", walletSummary1);

        when(walletComparisonService.compare(any(WalletSummary.class), any(WalletSummary.class)))
                .thenCallRealMethod();
        when(walletComparisonService.findMatchingWalletCurrency(anyList(), any(WalletSummary.class)))
                .thenCallRealMethod();

        when(walletSummaryRetrieverService.getWalletSummaries())
                .thenReturn(walletSummary2);

        WalletComparisonSummary actual = classUnderTest.getWalletSummary();


        assertNotNull(actual);
        verify(walletComparisonService).compare(any(WalletSummary.class), any(WalletSummary.class));
        assertFalse(actual.getWalletComparisons().isEmpty());
        assertEquals(currency1, actual.getWalletComparisons().get(0).getCurrency());
    }

    @Test
    public void testGetWalletComparisons_MultipleWallets() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        List<WalletSummary> walletSummary1 = new ArrayList<WalletSummary>();
        walletSummary1.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(4)));
        walletSummary1.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency2)
                                .setAvailable(BigDecimal.valueOf(4))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(1)));
        List<WalletSummary> walletSummary2 = new ArrayList<WalletSummary>();
        walletSummary2.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency1)
                                .setAvailable(BigDecimal.valueOf(2))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(6)));
        walletSummary2.add(new WalletSummary()
                .setBalance(
                        new Balance()
                                .setCurrency(currency2)
                                .setAvailable(BigDecimal.valueOf(4))
                                .setOnOrder(BigDecimal.ZERO))
                .setBtcValue(BigDecimal.valueOf(2)));

        ReflectionTestUtils.setField(classUnderTest, "newWalletSummaries", walletSummary1);

        when(walletSummaryRetrieverService.getWalletSummaries())
                .thenReturn(walletSummary2);
        when(walletComparisonService.compare(any(WalletSummary.class), any(WalletSummary.class)))
                .thenCallRealMethod();
        when(walletComparisonService.findMatchingWalletCurrency(anyList(), any(WalletSummary.class)))
                .thenCallRealMethod();

        WalletComparisonSummary actual = classUnderTest.getWalletSummary();


        assertNotNull(actual);
        verify(walletComparisonService, atLeast(1)).compare(any(WalletSummary.class), any(WalletSummary.class));
        assertFalse(actual.getWalletComparisons().isEmpty());
        assertEquals(2, actual.getWalletComparisons().size());
        assertEquals(currency1, actual.getWalletComparisons().get(0).getCurrency());
        assertEquals(currency2, actual.getWalletComparisons().get(1).getCurrency());
    }

}
