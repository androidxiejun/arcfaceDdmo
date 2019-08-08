package com.example.testliteorm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.testliteorm.bean.Student;
import com.example.testliteorm.sql.OrmDBHelper;
import com.litesuits.orm.LiteOrm;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnAdd, mBtnChange, mBtnDel, mBtnDelAll, mBtnQuery, mBtnQueryAll;
    private OrmDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initOrm();
        initView();
    }

    private void initOrm() {
        mHelper = OrmDBHelper.getInstance();
        mHelper.init(this.getApplicationContext());
    }

    private void initView() {
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnChange = findViewById(R.id.btn_change);
        mBtnDel = findViewById(R.id.btn_del);
        mBtnDelAll = findViewById(R.id.btn_del_all);
        mBtnQuery = findViewById(R.id.btn_query);
        mBtnQueryAll = findViewById(R.id.btn_query_all);

        mBtnAdd.setOnClickListener(this);
        mBtnChange.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        mBtnDelAll.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnQueryAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addStudent();
                break;
            case R.id.btn_change:
                break;
            case R.id.btn_del:
                break;
            case R.id.btn_del_all:
                deleteAll();
                break;
            case R.id.btn_query:
                queryStudent();
                break;
            case R.id.btn_query_all:
                queryAll();
                break;
        }
    }

    private void addStudent() {
        Student student = new Student();
        student.setAge(18);
        student.setName("xj");
        mHelper.insert(student);
    }

    private void queryStudent() {
        List<Student> students = mHelper.getQueryByWhere(Student.class, "name", new String[]{"xj"});
        for (Student student : students) {
            Log.d(TAG, "学生名字------" + student.getName());
            Log.d(TAG, "学生年龄------" + student.getAge());
            Log.d(TAG, "学生id------" + student.getId());
        }
    }

    private void queryAll() {
        List<Student> students = mHelper.getQueryAll(Student.class);
        for (Student student : students) {
            Log.d(TAG, "学生名字--all----" + student.getName());
            Log.d(TAG, "学生年龄--all----" + student.getAge());
            Log.d(TAG, "学生id--all----" + student.getId());
        }
    }

    private void deleteAll() {
//        mHelper.delete(Student.class);
//        mHelper.deleteDatabase();

        List<Student> students = mHelper.getQueryAll(Student.class);
        mHelper.deleteList(students);

    }

}
