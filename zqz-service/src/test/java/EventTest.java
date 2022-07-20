import com.zqz.service.ApplicationStart;
import com.zqz.service.event.service.OrderEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: EventTest
 * @Date: Created in 18:29 2022-7-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class EventTest {
    @Autowired
    private OrderEventService orderEventService;

    @Test
    public void testOrderEvent(){
        orderEventService.processOrder("4534645335232312");
    }


}
