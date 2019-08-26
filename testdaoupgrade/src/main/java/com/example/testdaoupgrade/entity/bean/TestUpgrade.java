package com.example.testdaoupgrade.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by AndroidXJ on 2019/8/23.
 */
@Entity(nameInDb = "db_test2")
public class TestUpgrade {
    @Id
    private Long id;
    private String name;
    private String address;
    private String sex;
    private String score;
    private int age;
    @Generated(hash = 1775324496)
    public TestUpgrade(Long id, String name, String address, String sex,
            String score, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.score = score;
        this.age = age;
    }
    @Generated(hash = 742174757)
    public TestUpgrade() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getScore() {
        return this.score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
   
  
}
