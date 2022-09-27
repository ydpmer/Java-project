package com.YuDp.utils;

import com.YuDp.entity.Student;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MyTableData {
    public static DefaultTableModel getModel(List<Student> students) {
        Object[] columnNames = new Object[]{"学号", "姓名", "电话", "爱好"};

        Object[][] data = new Object[students.size()][columnNames.length];
        for (int i = 0; i < students.size(); i++) {
            data[i][0] = students.get(i).getId();
            data[i][1] = students.get(i).getName();
            data[i][2] = students.get(i).getTel();
            data[i][3] = students.get(i).getHobby();
        }

        return new DefaultTableModel(data, columnNames);
    }
}
