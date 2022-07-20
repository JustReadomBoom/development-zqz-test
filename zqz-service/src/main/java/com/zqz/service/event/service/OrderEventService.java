package com.zqz.service.event.service;

import com.zqz.service.event.bean.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderEventService
 * @Date: Created in 18:18 2022-7-12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventService {

    @Autowired
    private ApplicationContext applicationContext;


    public String processOrder(String orderId) {
        long startTime = System.currentTimeMillis();
        applicationContext.publishEvent(new OrderEvent(this, orderId));
        long endTime = System.currentTimeMillis();
        log.info("processOrder订单处理完成时间:{}ms", endTime - startTime);
        return "购买成功";
    }
}
