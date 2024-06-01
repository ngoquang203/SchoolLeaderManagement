package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schoolleadermanagement.AdapterManagement.AdapterClassSituation;
import com.example.schoolleadermanagement.DataManagement.Student;
import com.example.schoolleadermanagement.DataManagement.TuitionReport;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClassSituation extends AppCompatActivity {
    // tạo view
    private ImageButton buttonBack;
    private ListView listView;
    private TextView textUserClass,textNameClass;
    private ArrayList<Student> studentArrayList;
    private ArrayList<TuitionReport> reportArrayList;
    private AdapterClassSituation adapterClassSituation;
    private SharedPreferences sharedPreferences;
    private String Classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_situation);
        Init(); // hàm khởi tạo giá trị
        clickBackPage(); // hàm quay về trang chủ
    }

    // hàm quay về trang chủ
    private void clickBackPage() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassSituation.this, MainActivity.class); // code quay lại trang chủ
                startActivity(intent);
            }
        });
    }
    // hàm khởi tạo giá trị
    private void Init() {
        // ánh xạ View
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        Classes = sharedPreferences.getString("Manager"," ");
        buttonBack = findViewById(R.id.classSituation_back);
        listView = findViewById(R.id.classSituation_listview);
        textUserClass = findViewById(R.id.classSituation_textUserClass);
        textNameClass = findViewById(R.id.classSituation_textNameClass);
        try {
            studentArrayList = Student.getuserlist(Classes); // lấy sinh viên có trong lớp
            reportArrayList = TuitionReport.getuserlist(Classes); // lấy sinh viên đã đóng học phí
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        adapterClassSituation = new AdapterClassSituation(this,studentArrayList,reportArrayList,Classes);
        listView.setAdapter(adapterClassSituation);
        textUserClass.setText(String.valueOf(studentArrayList.size() - reportArrayList.size()));
        textNameClass.setText(Classes);
    }
}