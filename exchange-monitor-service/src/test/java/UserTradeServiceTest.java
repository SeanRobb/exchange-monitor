import com.xeiam.xchange.dto.trade.UserTrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.exchange.monitor.common.RecentUserTrade;
import work.hoodie.exchange.monitor.data.dao.UserTradeDao;
import work.hoodie.exchange.monitor.service.trade.recent.RecentUserTradeMarshaller;
import work.hoodie.exchange.monitor.service.trade.recent.UserTradeService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTradeServiceTest {
    @InjectMocks
    private UserTradeService classUnderTest;

    @Mock
    private UserTradeDao userTradeDao;

    @Mock
    private RecentUserTradeMarshaller recentUserTradeMarshaller;

    @Mock
    private List<UserTrade> userTrades;

    @Mock
    private RecentUserTrade recentUserTrade;
    @Mock
    private RecentUserTrade recentUserTrade1;

    @Test
    public void save_1() throws Exception {

        List<RecentUserTrade> myList = new ArrayList<>();
        myList.add(recentUserTrade);


        when(recentUserTradeMarshaller.convert(userTrades))
                .thenReturn(myList);


        classUnderTest.save(userTrades);

        verify(recentUserTradeMarshaller).convert(userTrades);
        verify(userTradeDao).save(recentUserTrade);

    }


    @Test
    public void save_2() throws Exception {

        List<RecentUserTrade> myList = new ArrayList<>();
        myList.add(recentUserTrade);
        myList.add(recentUserTrade1);


        when(recentUserTradeMarshaller.convert(userTrades))
                .thenReturn(myList);


        classUnderTest.save(userTrades);

        verify(recentUserTradeMarshaller).convert(userTrades);
        verify(userTradeDao).save(recentUserTrade);
        verify(userTradeDao).save(recentUserTrade1);

    }

}
