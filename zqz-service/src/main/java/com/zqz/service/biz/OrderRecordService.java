package com.zqz.service.biz;

import com.zqz.service.entity.OrderRecord;
import com.zqz.service.mapper.OrderRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderRecordService
 * @Date: Created in 14:05 2022-6-21
 */
@Service
public class OrderRecordService {

    @Resource
    private OrderRecordMapper mapper;

    public int insertBatch(List<OrderRecord> list){
        return mapper.insertBatch(list);
    }
}
