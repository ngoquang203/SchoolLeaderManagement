package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // khơi tạo biến
    private Button watchTuitionReportClass,watchTuitionReportDepartment,logOut;
    private SharedPreferences sharedPreferences;
    private String Position,Manager;
    private TextView textPosition,textManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init(); // hàm khởi tạo giá trị
        setVisibleClassOrDepartment(); // hàm hiển thị 1 trong 2 button
        clickButtonWatchClass(); // hàm xử lí sự kiện click
        clickButtonWatchDepartment(); // hàm xử lí sự kiện click
    }


    private void setVisibleClassOrDepartment() {
        if(Position.equals("Giáo viên")){
            // ẩn chức năng của lãnh đạo khoa
            watchTuitionReportClass.setVisibility(View.VISIBLE);
            watchTuitionReportDepartment.setVisibility(View.GONE);
        }else{
            // ẩn chức năng của giáo viên
            watchTuitionReportClass.setVisibility(View.GONE);
            watchTuitionReportDepartment.setVisibility(View.VISIBLE);
        }
    }


    private void clickButtonWatchDepartment() {
        watchTuitionReportDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartmentSituation.class); // tạo intent chuyển màn hình
                startActivity(intent);
            }
        });
    }

    private void clickButtonWatchClass() {
        watchTuitionReportClass.setOnClickListener(new View.OnClickListener() { // chuyển màn hình sang xem báo cáo học phí
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassSituation.class); // tạo intent chuyển màn hình
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() { // chuyển màn hình về đăng nhập
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class); // tạo intent chuyển màn hình
                startActivity(intent);
            }
        });
    }

    private void Init() {
        // ánh xạ View
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        Position = sharedPreferences.getString("Position"," "); // lấy chức vụ
        Manager = sharedPreferences.getString("Manager"," "); // lấy quản lí

        watchTuitionReportClass = findViewById(R.id.main_watchTuitionReportClass);
        watchTuitionReportDepartment = findViewById(R.id.main_watchTuitionReportDepartment);
        textPosition = findViewById(R.id.main_textPosition);
        textManager = findViewById(R.id.main_textManager);

        textPosition.setText(Position); // set data cho text view chức vụ
        textManager.setText(Manager); // set data cho text view quản lí
        logOut = findViewById(R.id.main_logOut);
    }
}