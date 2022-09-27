package com.YuDp.service;

import com.YuDp.entity.Student;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    void addStudent(Student student);

    // 删除多条
    int removeStudents(ArrayList<Integer> ids);

    // 删除选中数据
    void removeStudent(int id);

    // 修改数据
    void updateStudent(Student student);

    // 根据姓名模糊查询数据
    List<Student> queryStudent(String name);
    // 加载全部数据（从文件中）
    List<Student> getList();

    // 退出时保存数据
    void saveData();
}
