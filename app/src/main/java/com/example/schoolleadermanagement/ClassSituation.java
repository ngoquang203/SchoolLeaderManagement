package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Spinner spinner;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_situation);
        Init(); // hàm khởi tạo giá trị
        clickBackPage(); // hàm quay về trang chủ
        clickSpiner(); // hàm thay đổi báo cáo khi chọn spinner
    }

    private void clickSpiner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    reportArrayList = TuitionReport.getuserlist(Classes,getSemesterInSpinner(stringArrayList.get(position)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                adapterClassSituation = new AdapterClassSituation(ClassSituation.this,studentArrayList,reportArrayList,Classes,getSemesterInSpinner(stringArrayList.get(position)));
                listView.setAdapter(adapterClassSituation);
                textUserClass.setText(String.valueOf(studentArrayList.size() - reportArrayList.size()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // hàm lấy ra kì học
    private String getSemesterInSpinner(String str){
        String semester = String.valueOf(str.charAt(3));
        return semester;
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
        stringArrayList = new ArrayList<>();
        stringArrayList.add("Kì 1");
        stringArrayList.add("Kì 2");
        try {
            studentArrayList = Student.getuserlist(Classes); // lấy sinh viên có trong lớp
            reportArrayList = TuitionReport.getuserlist(Classes,getSemesterInSpinner(stringArrayList.get(0))); // lấy sinh viên đã đóng học phí
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        textUserClass.setText(String.valueOf(studentArrayList.size() - reportArrayList.size()));
        textNameClass.setText(Classes);
        spinner = findViewById(R.id.classSituation_spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringArrayList);
        spinner.setAdapter(adapter);
        adapterClassSituation = new AdapterClassSituation(this,studentArrayList,reportArrayList,Classes,getSemesterInSpinner(stringArrayList.get(0)));
        listView.setAdapter(adapterClassSituation);
    }
}