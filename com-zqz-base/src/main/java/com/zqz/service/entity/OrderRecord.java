package com.zqz.service.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderRecord
 * @Date: Created in 13:48 2022-6-21
 */
@Data
public class OrderRecord {
    private Integer id;
    private String orderId;
    private String name;
    private BigDecimal amount;
    private String address;
    private Date cTime;
}
