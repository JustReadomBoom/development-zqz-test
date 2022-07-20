package com.zqz.service.event.listener;

import com.zqz.service.event.bean.OrderEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderEventListener
 * @Date: Created in 18:07 2022-7-12
 */
@Component
@Slf4j
public class OrderEventListener implements ApplicationListener<OrderEvent> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        String orderId = orderEvent.getOrderId();
        long startTime = System.currentTimeMillis();
        Thread.sleep(2000);
        long endTime = System.currentTimeMillis();
        log.info("onApplicationEvent订单处理完毕，orderId:{}, 处理时间:{}ms", orderId, (endTime - startTime));
    }
}
