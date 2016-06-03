import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.OpenOrder;
import work.hoodie.exchange.monitor.service.balance.BalanceMarshaller;
import work.hoodie.exchange.monitor.service.balance.BalanceRetrieverService;
import work.hoodie.exchange.monitor.service.trade.open.OpenOrderRetriever;
import work.hoodie.exchange.monitor.service.trade.open.OpenOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceRetrieverServiceTest {
    @InjectMocks
    private BalanceRetrieverService classUnderTest;
    @Mock
    private PollingAccountService pollingAccountService;
    @Mock
    private OpenOrderRetriever openOrderRetriever;
    @Mock
    private OpenOrderService openOrderService;
    @Mock
    private BalanceMarshaller balanceMarshaller;
    @Mock
    private OpenOrder openOrder;
    private String currency1 = "Test1";
    private String currency2 = "Test2";
    private String currency3 = "Test3";
    private String currency4 = "Test4";

    private Map<String, Wallet> getWallets() {
        Map<String, Wallet> wallets = new HashMap<String, Wallet>();
        wallets.put(currency1, new Wallet(currency1, BigDecimal.valueOf(1)));
        wallets.put(currency2, new Wallet(currency2, BigDecimal.valueOf(2)));
        wallets.put(currency3, new Wallet(currency3, BigDecimal.valueOf(3)));
        wallets.put(currency4, new Wallet(currency4, BigDecimal.valueOf(4)));
        return wallets;
    }

    @Test
    public void getBalances() throws Exception {
        Map<String, Wallet> wallets = getWallets();
        List<OpenOrder> openOrders = new ArrayList<OpenOrder>();
        AccountInfo accountInfo = new AccountInfo("My UserName", wallets);

        when(pollingAccountService.getAccountInfo())
                .thenReturn(accountInfo);
        when(openOrderRetriever.getOpenOrders())
                .thenReturn(openOrders);
        when(openOrderService.findTotalOpenOrdersForCurrency(anyList(),anyString()))
                .thenReturn(openOrder);


        List<Balance> balances = classUnderTest.getBalances();


        assertNotNull(balances);
        verify(pollingAccountService).getAccountInfo();
        verify(openOrderRetriever).getOpenOrders();
        assertEquals(wallets.size(), balances.size());
        assertFalse(wallets.isEmpty());
        for (Wallet wallet : wallets.values()) {
            verify(balanceMarshaller).getBalance(eq(wallet), eq(openOrder));
        }
    }

}
