package com.zqz.service.controller;

import com.zqz.service.biz.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: InsertBatchController
 * @Date: Created in 14:44 2022-6-21
 */
@RestController
@RequestMapping("/batch")
public class InsertBatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping("/start")
    public Boolean batchStart(){
        return batchService.insertBatch();
    }
}
