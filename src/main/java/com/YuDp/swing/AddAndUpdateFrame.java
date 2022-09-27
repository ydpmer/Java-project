package com.YuDp.swing;

import cn.hutool.core.util.StrUtil;
import com.YuDp.entity.Student;
import com.YuDp.service.StudentService;
import com.YuDp.service.impl.StudentServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddAndUpdateFrame extends JFrame {
    private StudentService studentService;
    private MainFrame mainFrame;
    private String title;
    private String confirm;
    private Student student;
    private int type;

    JTextField id, name, tel, hobby;
    JButton add, reset;

    JLabel idLabel, nameLabel, telLabel, hobbyLabel;

    // 将主面板传入，方便数据传递。 type用于判断是添加数据还是修改数据
    public AddAndUpdateFrame(final MainFrame mainFrame, int type) throws HeadlessException {
        this.type = type;
        title = "添加学生";
        confirm = "添加";

        this.mainFrame = mainFrame;
        studentService = new StudentServiceImpl();
        setBounds(500, 280, 250, 300);
        setTitle(title);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        addCom();

        this.setVisible(true);
    }

    // 构造方法重载，若传入一个student对象，则表示需要修改此对象。
    public AddAndUpdateFrame(final MainFrame mainFrame, int type, Student student) throws HeadlessException {
        this.student = student;
        this.type = type;
        title = "修改学生";
        confirm = "修改";
        this.mainFrame = mainFrame;
        studentService = new StudentServiceImpl();
        setBounds(500, 280, 400, 500);
        setTitle(title);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        addCom();

        this.setVisible(true);
    }

    public void addCom() {
        id = new JTextField();
        id.setBounds(70, 40, 150, 30);

        name = new JTextField();
        name.setBounds(70, 80, 150, 30);

        tel = new JTextField();
        tel.setBounds(70, 120, 150, 30);

        hobby = new JTextField();
        hobby.setBounds(70, 160, 150, 30);

        add = new JButton(confirm);
        add.setBounds(30, 210, 75, 30);

        reset = new JButton("重置");
        reset.setBounds(140, 210, 75, 30);
        idLabel = new JLabel("学号：");
        idLabel.setBounds(20, 40, 75, 30);

        nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(20, 80, 75, 30);

        telLabel = new JLabel("电话：");
        telLabel.setBounds(20, 120, 75, 30);

        hobbyLabel = new JLabel("爱好：");
        hobbyLabel.setBounds(20, 160, 75, 30);
        if (type == 2) {
            id.setEditable(false);
            id.setText(student.getId());
            name.setText(student.getName());
            tel.setText(student.getTel());
            hobby.setText(student.getHobby());
        }
        addListener();
        add(id);
        add(name);
        add(hobby);
        add(tel);
        add(add);
        add(idLabel);
        add(nameLabel);
        add(telLabel);
        add(hobbyLabel);
        add(reset);
    }

    // 为控件添加事件监听
    public void addListener() {
        // 根据type判断添加或是修改
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (type) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                }
            }
        });
        // 重置输入
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
            }

            private void reset() {
                if (type == 1) {
                    id.setText("");
                }
                name.setText("");
                tel.setText("");
                hobby.setText("");
            }
        });
    }

    private void addStudent() {
        if (!validation()) return;
        String id = this.id.getText().trim();
        try {
            Integer.parseInt(id);
            student = new Student(id, name.getText().trim(), tel.getText().trim(), hobby.getText().trim());
            studentService.addStudent(student);
            mainFrame.showData(MainFrame.students);
            dispose();
        } catch (Exception ex) {
            System.out.println("id输入错误");
        }
    }

    private void updateStudent() {
        if (!validation()) return;
        student = new Student(id.getText().trim(), name.getText().trim(), tel.getText().trim(), hobby.getText().trim());
        studentService.updateStudent(student);
        mainFrame.showData(MainFrame.students);
        dispose();
    }

    // 用于验证数据是否为空
    private boolean validation() {
        boolean flag = true;
        String id = this.id.getText().trim();
        String name = this.name.getText().trim();
        String tel = this.tel.getText().trim();
        String hobby = this.hobby.getText().trim();
        if (StrUtil.isBlank(id) || StrUtil.isBlank(name) || StrUtil.isBlank(tel) || StrUtil.isBlank(hobby)) {
            flag = false;
            System.out.println("属性不能为空！");
        }
        return flag;
    }
}
