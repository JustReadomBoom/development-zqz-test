package com.zqz.service.event.bean;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderEvent
 * @Date: Created in 18:04 2022-7-12
 */
public class OrderEvent extends ApplicationEvent {

    private String orderId;

    public OrderEvent(Object source, String orderId) {
        super(source);
        this.orderId = orderId;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
