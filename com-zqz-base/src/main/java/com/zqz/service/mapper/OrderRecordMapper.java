package com.zqz.service.mapper;

import com.zqz.service.entity.OrderRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderRecordMapper
 * @Date: Created in 13:52 2022-6-21
 */
@Mapper
public interface OrderRecordMapper {

    int insertBatch(List<OrderRecord> list);

}
