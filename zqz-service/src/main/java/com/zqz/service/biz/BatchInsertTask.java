package com.zqz.service.biz;

import com.zqz.service.entity.OrderRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ZQZ
 * @Description: 批量插入任务
 * @ClassName: BatchInsertTask
 * @Date: Created in 14:03 2022-6-21
 */
@Slf4j
public class BatchInsertTask implements Callable<Boolean> {
    private static final int BATCH100 = 100;
    private List<OrderRecord> recordList;
    private OrderRecordService orderRecordService;

    /**
     * 创建自适应机器本身线程数量的线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            Runtime.getRuntime().availableProcessors(),
            2L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public BatchInsertTask(List<OrderRecord> recordList, OrderRecordService orderRecordService) {
        this.recordList = recordList;
        this.orderRecordService = orderRecordService;
    }


    @Override
    public Boolean call() throws Exception {
        log.info("开始执行插入数据...");
        long startTime = System.currentTimeMillis();
        try {
            batchOp(recordList);
        } catch (Exception e) {
            log.error("批量任务异常:{}", e.getMessage(), e);
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("插入数据完成时间:{}ms", endTime - startTime);
        }
        return true;
    }


    /**
     * 批量执行
     *
     * @param list
     */
    private void batchOp(List<OrderRecord> list) {
        if (!CollectionUtils.isEmpty(list)) {
            int size = list.size();
            if (size <= BATCH100) {
                orderRecordService.insertBatch(list);
            } else {
                batchOpSplit(list);
            }
        }

    }

    /**
     * 切割数据
     *
     * @param list
     */
    private void batchOpSplit(List<OrderRecord> list) {
        log.info("开始切割数据...");
        List<List<OrderRecord>> pagingList = pagingList(list);
        try {
            for (List<OrderRecord> records : pagingList) {
                threadPoolExecutor.execute(() -> {
                    batchOp(records);
                });
            }
        } catch (Exception e) {
            log.error("切割数据异常:{}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            threadPoolExecutor.shutdown();
            log.info("切割数据完成");
        }


    }

    /**
     * 数据分片
     *
     * @param list
     * @param <T>
     * @return
     */
    private static <T> List<List<T>> pagingList(List<T> list) {
        int pageSize = BATCH100;
        int length = list.size();
        int num = (length + pageSize - 1) / pageSize;
        List<List<T>> newList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int fromIndex = i * pageSize;
            int toIndex = (i + 1) * pageSize < length ? (i + 1) * pageSize : length;
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }
}
