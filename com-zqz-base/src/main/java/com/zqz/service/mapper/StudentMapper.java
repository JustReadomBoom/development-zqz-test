package com.zqz.service.mapper;

import com.zqz.service.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StudentMapper
 * @Date: Created in 16:38 2022/5/6
 */
@Mapper
public interface StudentMapper {

    Student queryById(Integer id);

    int saveStudent(Student student);
}
