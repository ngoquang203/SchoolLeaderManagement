package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolleadermanagement.DataManagement.Logins;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    // khởi tạo biến
    private TextInputEditText ID,PassWords,ComfirmPassword;
    private Button buttonInsertUser;
    private ImageButton buttonBack;
    private Spinner spinnerPosition,spinnerManager;

    private ArrayList<String> arrayListPosition;
    private ArrayList<String> arrayListManagement;
    private ArrayAdapter<String> adapterPosition;
    private ArrayAdapter<String> adapterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Init(); // hàm khởi tạo giá trị
        clickButtonBack(); // hàm quay trở lại trang chính
        clickButtonInsertUser(); // hàm tạo người dùng mới
        setCLickSpinnerPosition(); // hàm thay đổi giá trị của spinner manager
    }

    // hàm chuyển màn hình đăng nhập
    private void changeToPageLogin(){
        Intent intent = new Intent(SignUp.this,Login.class); // code chuyển màn hình
        startActivity(intent);
    }

    // hàm tạo người dùng mới
    private void clickButtonInsertUser() {
        buttonInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ID.getText().toString();
                String passWords = PassWords.getText().toString();
                String confirmPassword = ComfirmPassword.getText().toString();
                Logins logins = new Logins(); // tạo đối tượng login
                if (!id.isEmpty() && !passWords.isEmpty() && !confirmPassword.isEmpty()){ // kiểm tra nhập đầy đủ thông tin chưa
                    try {
                        logins = Logins.getuserlist(id,passWords); // gán login với dữ liệu lấy từ sql
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(id.isEmpty() || passWords.isEmpty() || confirmPassword.isEmpty()){ // kiểm tra chưa có thông tin nhập vào thì thông báo
                    Toast.makeText(SignUp.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();


                }else if(id.equals(logins.getID())){
                    Toast.makeText(SignUp.this, "Đã có tài khoản đăng kí ID này", Toast.LENGTH_SHORT).show();
                }else if(passWords.equals(confirmPassword)){ // kiểm tra mật khẩu và xác nhận mật khẩu giống nhau
                    try {
                        Logins.insertList(
                                id,
                                passWords,
                                arrayListPosition.get(spinnerPosition.getSelectedItemPosition()),
                                arrayListManagement.get(spinnerManager.getSelectedItemPosition())); // Lưu tài khoản mới vào SQL
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(SignUp.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show(); // thông báo tạo tài khoản thành công
                    changeToPageLogin(); // hàm chuyển màn hình đăng nhập
                }else if(!passWords.equals(confirmPassword)){ // kiểm tra mật khẩu và xác nhận mật khẩu k giống nhau
                    Toast.makeText(SignUp.this, "Mật khẩu và mật khẩu xác nhận chưa đồng nhất", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // hàm quay trở lại trang chính
    private void clickButtonBack() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPageLogin(); // hàm chuyển màn hình đăng nhập
            }
        });
    }

    // hàm in ra set giá trị cho arraylist
    private void setArrayListPosition(){
        // thêm dữ liệu chức vụ
        arrayListPosition = new ArrayList<>();
        arrayListPosition.add("Giáo viên");
        arrayListPosition.add("Lãnh đạo khoa");
    }

    private void setArrayListManagerTecher(){
        // thêm dữ liệu của lớp
        arrayListManagement = new ArrayList<>();
        arrayListManagement.add("D.10.48.01");
        arrayListManagement.add("D.10.48.02");
        arrayListManagement.add("D.10.48.03");
        arrayListManagement.add("D.10.48.04");
        arrayListManagement.add("D.10.48.05");

        arrayListManagement.add("D.10.38.01");
        arrayListManagement.add("D.10.38.02");
        arrayListManagement.add("D.10.38.03");
        arrayListManagement.add("D.10.38.04");
        arrayListManagement.add("D.10.38.05");

        arrayListManagement.add("D.10.28.01");
        arrayListManagement.add("D.10.28.02");
        arrayListManagement.add("D.10.28.03");
        arrayListManagement.add("D.10.28.04");
        arrayListManagement.add("D.10.28.05");
    }

    private void setArrayListManagerDepartment(){
        // thêm dữ liệu khoa
        arrayListManagement = new ArrayList<>();
        arrayListManagement.add("CNTT");
        arrayListManagement.add("TCNH");
        arrayListManagement.add("QTKD");
    }

    private void setCLickSpinnerPosition(){
        // xử lí spinner
        spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(arrayListPosition.get(position).equals("Giáo viên")){
                    // chọn chức vụ giáo viên sẽ quản lí lớp
                    setArrayListManagerTecher();
                    adapterManager = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item,arrayListManagement);
                    spinnerManager.setAdapter(adapterManager);
                }else{
                    // chọn chức vụ lãnh đạo khoa sẽ quản lí các lớp
                    setArrayListManagerDepartment();
                    adapterManager = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_dropdown_item,arrayListManagement);
                    spinnerManager.setAdapter(adapterManager);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // hàm khởi tạo giá trị
    private void Init() {
        // ánh xạ View
        ID = findViewById(R.id.signUp_user);
        PassWords = findViewById(R.id.signUp_password);
        ComfirmPassword = findViewById(R.id.signUp_ComfirmPassword);
        buttonInsertUser = findViewById(R.id.signUp_button);
        buttonBack = findViewById(R.id.signUp_back);
        spinnerPosition = findViewById(R.id.signUp_spinnerPosition);
        spinnerManager = findViewById(R.id.signUp_spinnerManager);
        setArrayListPosition();
        setArrayListManagerTecher();
        adapterPosition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayListPosition);
        spinnerPosition.setAdapter(adapterPosition);
        adapterManager = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayListManagement);
        spinnerManager.setAdapter(adapterManager);
    }
}