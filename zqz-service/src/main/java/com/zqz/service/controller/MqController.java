package com.zqz.service.controller;

import com.zqz.service.biz.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: MqController
 * @Date: Created in 11:41 2022/5/25
 */
@RestController
@RequestMapping("/mq")
public class MqController {


    @Autowired
    private MqProducer mqProducer;

    @PostMapping("/send")
    public String send(@RequestBody String json) {
        mqProducer.sendMsg(json);
        return "OK";
    }
}
