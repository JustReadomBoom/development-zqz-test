package com.zqz.service.biz;

import com.zqz.service.entity.OrderRecord;
import com.zqz.service.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: BatchService
 * @Date: Created in 13:50 2022-6-21
 */
@Service
@Slf4j
public class BatchService {
    @Autowired
    private OrderRecordService recordService;

    private List<OrderRecord> list = new ArrayList<>(1000000);

    /**
     * 创建自适应机器本身线程数量的线程池
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5,
            Runtime.getRuntime().availableProcessors(),
            2L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 执行入口
     * @return
     */
    public boolean insertBatch() {
        createData();
        try {
            Future<Boolean> future = executor.submit(new BatchInsertTask(list, recordService));
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 初始化数据
     */
    private void createData() {
        for (int i = 0; i < 3000000; i++) {
            OrderRecord record = new OrderRecord();
            record.setOrderId(RandomUtil.createRandom(true, 12));
            record.setName(RandomUtil.getRandomString(5));
            record.setAddress(RandomUtil.getRandomString(16));
            record.setAmount(BigDecimal.TEN);
            list.add(record);
        }
    }


}
