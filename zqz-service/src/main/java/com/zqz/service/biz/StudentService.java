package com.zqz.service.biz;

import com.zqz.service.entity.Student;
import com.zqz.service.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StudentService
 * @Date: Created in 16:53 2022/5/6
 */
@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public Student queryById(Integer id) {
        return studentMapper.queryById(id);
    }

    public int saveStudent(Student student) {
        return studentMapper.saveStudent(student);
    }
}
