package com.YuDp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 存文件必须序列化
public class Student implements Serializable {

    private String Id;
    private String name;
    private String tel;
    private String hobby;

    // 重写equals方法，方便两个Student比较
    @Override
    public boolean equals(Object obj) {
        Student student = (Student) obj;
        return student.getId().equals(this.Id);
    }
}
