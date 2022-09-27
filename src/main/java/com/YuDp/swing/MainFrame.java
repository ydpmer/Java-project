package com.YuDp.swing;

import com.YuDp.entity.Student;
import com.YuDp.service.StudentService;
import com.YuDp.service.impl.StudentServiceImpl;
import com.YuDp.utils.MyTableData;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame {
    private StudentService studentService;
    // 数据存储在students集合中，关闭程序时存入文件，启动程序时将数据载入students
    public static List<Student> students;
    private MainFrame mainFrame = this;
    JTable jTable;
    JScrollPane jScrollPane;
    JButton add, del, update, query;

    JTextField jTextField;

    public MainFrame() {
        // 提供增删改查方法的对象
        studentService = new StudentServiceImpl();
        // 载入数据
        students = studentService.getList();
        this.setBounds(400, 200, 500, 400);
        this.setTitle("信息管理");
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        this.addCom();
        this.showData(students);
        this.setVisible(true);
    }

    public void addCom() {
        jTable = new JTable();
        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(2, 2, 491, 250);

        add = new JButton("添加");
        add.setBounds(20, 330, 80, 25);

        update = new JButton("修改");
        update.setBounds(150, 330, 80, 25);

        del = new JButton("删除");
        del.setBounds(280, 330, 80, 25);

        query = new JButton("查询");
        query.setBounds(150, 280, 80, 25);

        jTextField = new JTextField();
        jTextField.setBounds(20, 280, 100, 25);

        addListener();
        add(add);
        add(update);
        add(del);
        add(query);
        add(jTextField);
        add(jScrollPane);
    }

    // 不同的集合展示不同的数据
    public void showData(List<Student> data) {
        // 设置表格数据模型
        jTable.setModel(MyTableData.getModel(data));
        // 表格数据变化时，修改数据
        jTable.getModel().addTableModelListener(e -> {
            studentService.updateStudent(getCurrentStudent());
        });
    }

    // 为控件添加事件监听
    public void addListener() {
        addStudent();
        delStudent();
        queryStudent();
        updateStudent();
        // 关闭窗口时，保存数据到文件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                studentService.saveData();
            }
        });
    }

    public void addStudent() {
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AddAndUpdateFrame(mainFrame, 1);
            }
        });
    }

    public void delStudent() {
        del.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] rows = jTable.getSelectedRows();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (int i : rows) {
                    int id = Integer.parseInt((String) jTable.getValueAt(i, 0));
                    ids.add(id);
                }
                studentService.removeStudents(ids);
                showData(students);
            }
        });
    }

    public void updateStudent() {
        update.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getCurrentStudent() != null) {
                    new AddAndUpdateFrame(mainFrame, 2, getCurrentStudent());
                }
            }
        });
    }

    public void queryStudent() {
        query.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<Student> list = studentService.queryStudent(jTextField.getText());
                showData(list);
            }
        });
    }

    // 获取当前选中的数据对象
    public Student getCurrentStudent() {
        int row = jTable.getSelectedRow();
        if (row == -1) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("请选择");
                return null;
            }
        }
        return new Student((String) jTable.getValueAt(row, 0), (String) jTable.getValueAt(row, 1), (String) jTable.getValueAt(row, 2), (String) jTable.getValueAt(row, 3));
    }
}
