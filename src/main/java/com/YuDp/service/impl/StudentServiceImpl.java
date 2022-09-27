package com.YuDp.service.impl;

import com.YuDp.entity.Student;
import com.YuDp.service.StudentService;
import com.YuDp.swing.MainFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private List<Student> students = MainFrame.students;
    private final File file = new File("StudentData");

    @Override
    public void addStudent(Student student) {
        // 因为重写了Student的equals方法，所以可以之间判断
        if (!students.contains(student))
            students.add(student);
    }

    @Override
    public int removeStudents(ArrayList<Integer> ids) {
        Collections.sort(ids);
        ids.forEach(this::removeStudent);
        return ids.size();
    }

    @Override
    public void removeStudent(int id) {
        students.removeIf(student -> Integer.parseInt(student.getId()) == id);
    }


    @Override
    public void updateStudent(Student student) {
        if (!students.contains(student)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("id不可修改");
            }
        } else {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).equals(student)) {
                    students.set(i, student);
                }
            }
        }
    }

    @Override
    public List<Student> queryStudent(String name) {
        ArrayList<Student> list = new ArrayList<>();
        students.forEach(item -> {
            if (item.getName().contains(name.trim())) {
                list.add(item);
            }
        });
        System.out.println(list);
        return list;
    }

    @Override
    public List<Student> getList() {
        List<Student> list = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            list = (List<Student>) objIn.readObject();
            students = list;
            objIn.close();
            in.close();
        } catch (Exception e) {
        } finally {
            students = list;
        }
        return students;
    }

    @Override
    public void saveData() {
        File file = new File("StudentData");
        try {
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(students);
            objOut.flush();
            objOut.close();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
