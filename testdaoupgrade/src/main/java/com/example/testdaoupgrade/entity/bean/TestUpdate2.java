package com.example.testdaoupgrade.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AndroidXJ on 2019/8/23.
 */
@Entity(nameInDb = "db_data")
public class TestUpdate2 {
    @Id
    private Long id;
    private String data;
    private String data2;
    @Generated(hash = 485070165)
    public TestUpdate2(Long id, String data, String data2) {
        this.id = id;
        this.data = data;
        this.data2 = data2;
    }
    @Generated(hash = 1023993642)
    public TestUpdate2() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getData2() {
        return this.data2;
    }
    public void setData2(String data2) {
        this.data2 = data2;
    }
}
