package com.zqz.service.controller;

import com.alibaba.fastjson.JSON;
import com.zqz.service.entity.Student;
import com.zqz.service.biz.StudentService;
import com.zqz.service.utils.DateUtil;
import com.zqz.service.utils.HttpUtil;
import com.zqz.service.utils.RedisClient;
import com.zqz.service.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StudentController
 * @Date: Created in 16:47 2022/5/6
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private RedisLock redisLock;
    private static final String STUDENT_KEY = "student_save_";
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @GetMapping("/queryById")
    public Object getStudentById(@RequestParam("id") Integer id) {
        try {
            Student student = studentService.queryById(id);
            if (null == student) {
                log.info("student is null, id:{}", id);
                return Boolean.FALSE;
            }
            return JSON.toJSONString(student);
        } catch (Exception e) {
            log.error("getStudentById error:{}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @PostMapping("/saveStudent")
    public Object saveStudent(@RequestBody Student student) {
        try {
            int i = studentService.saveStudent(student);
            if (i <= 0) {
                log.info("saveStudent fail");
                return Boolean.FALSE;
            }
            incr(STUDENT_KEY);
            return getNum(STUDENT_KEY);
        } catch (Exception e) {
            log.error("saveStudent error:{}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    private void incr(String bizKey) {
        String key = bizKey + DateUtil.getDateFormat2Str(new Date());
        Long number = redisClient.getNumber(key);
        if (ObjectUtils.isEmpty(number)) {
            redisClient.setNumber(key, 1L);
        } else {
            redisClient.incr(key, 1L);
        }
    }

    private Integer getNum(String bizKey) {
        String key = bizKey + DateUtil.getDateFormat2Str(new Date());
        return Integer.valueOf(redisClient.get(key));
    }

    @PostMapping("/saveStudent/lock")
    public Object testRedisLock(@RequestBody Student student) {
        String key = null;
        String val = null;
        try {
            key = student.getName();
            val = student.getAddress();
            boolean lock = redisLock.tryLock(key, val, 5);
            if (lock) {
                return "提交成功!";
            } else {
                return "重复请求，请稍后再试!";
            }
        } catch (Exception e) {
            log.error("saveStudent test redis lock error:{}", e.getMessage(), e);
            return Boolean.FALSE;
        } finally {
            if (null != key) {
                redisLock.releaseLock(key, val);
            }
        }
    }


    @GetMapping("/test/submit")
    public Object testSubmit() {
        String url = "http://localhost:8888/student/saveStudent/lock";
        for (int i = 0; i < 50; i++) {
            executor.submit(() -> {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                Student student = new Student();
                student.setName("zqz");
                student.setAddress("hhsiahdlasj");
                student.setAge(28);
                String resp = HttpUtil.postJson(url, header, JSON.toJSONString(student));
                System.out.println("resp=" + resp);
            });
        }
        return Boolean.TRUE;
    }
}
