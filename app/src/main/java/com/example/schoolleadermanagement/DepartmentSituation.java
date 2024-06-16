package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolleadermanagement.AdapterManagement.AdapterClassSituation;
import com.example.schoolleadermanagement.DataManagement.Student;
import com.example.schoolleadermanagement.DataManagement.TuitionReport;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSituation extends AppCompatActivity {
    // tạo dữ View
    private ImageButton buttonBack;
    private SharedPreferences sharedPreferences;
    private String Department;
    private PieChart pieChart;
    private ArrayList<TuitionReport> reportArrayList;
    private ArrayList<Student> studentArrayList;
    private TextView class1,class2,class3,class4,class5;
    private TextView percent1,percent2,percent3,percent4,percent5;
    private TextView textNameDepartment;
    private DecimalFormat df = new DecimalFormat("#.##");
    private Spinner spinner;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_situation);
        Init(); // hàm khởi tạo giá trị
        clickBackPage(); // hàm quay về trang chủ
        setDataPieChart(); // hàm xét giá trị cho Pie chart
        setDataClass(getSemesterInSpinner(stringArrayList.get(0))); // hàm set giá trị phần trăm cho các lớp
        clickSpiner(); // hàm thay đổi báo cáo khi chọn spinner
    }
    private void clickSpiner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    reportArrayList = TuitionReport.getuserlistDepartment(Department,getSemesterInSpinner(stringArrayList.get(position)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                setDataClass(getSemesterInSpinner(stringArrayList.get(position)));
                setDataPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataPieChart() {
        // set data cho pie chart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Long.valueOf(reportArrayList.size()),"Đã nộp"));
        entries.add(new PieEntry(Long.valueOf(studentArrayList.size() - reportArrayList.size()),"Chưa nộp"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Pie Chart");
        // set hiển thị pie chart
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(16);
        pieDataSet.setValueLineColor(getResources().getColor(R.color.white));
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setSliceSpace(5f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.getLegend().setTextColor(getResources().getColor(R.color.white));
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    // hàm quay về trang chủ
    private void clickBackPage() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepartmentSituation.this, MainActivity.class); // code quay về trang chủ
                startActivity(intent);
            }
        });
    }

    // hàm set giá trị cho tên lớp và phần trăm sinh viên lớp đăng kí học
    private void setDataClass(String semester){
        ArrayList<TuitionReport> list1 = new ArrayList<>();
        ArrayList<TuitionReport> list2 = new ArrayList<>();
        ArrayList<TuitionReport> list3 = new ArrayList<>();
        ArrayList<TuitionReport> list4 = new ArrayList<>();
        ArrayList<TuitionReport> list5 = new ArrayList<>();
        if (Department.equals("CNTT")){
            try {
                list1 = TuitionReport.getuserlist("D.10.48.01",semester);
                list2 = TuitionReport.getuserlist("D.10.48.02",semester);
                list3 = TuitionReport.getuserlist("D.10.48.03",semester);
                list4 = TuitionReport.getuserlist("D.10.48.04",semester);
                list5 = TuitionReport.getuserlist("D.10.48.05",semester);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            class1.setText("D.10.48.01");
            class2.setText("D.10.48.02");
            class3.setText("D.10.48.03");
            class4.setText("D.10.48.04");
            class5.setText("D.10.48.05");

            String p1 = df.format(((list1.size()/10.0)*100)) + "%";
            String p2 = df.format(((list2.size()/10.0)*100)) + "%";
            String p3 = df.format(((list3.size()/10.0)*100)) + "%";
            String p4 = df.format(((list4.size()/10.0)*100)) + "%";
            String p5 = df.format(((list5.size()/10.0)*100)) + "%";
            percent1.setText(p1);
            percent2.setText(p2);
            percent3.setText(p3);
            percent4.setText(p4);
            percent5.setText(p5);
        }else if(Department.equals("TCNH")){
            try {
                list1 = TuitionReport.getuserlist("D.10.38.01",semester);
                list2 = TuitionReport.getuserlist("D.10.38.02",semester);
                list3 = TuitionReport.getuserlist("D.10.38.03",semester);
                list4 = TuitionReport.getuserlist("D.10.38.04",semester);
                list5 = TuitionReport.getuserlist("D.10.38.05",semester);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            class1.setText("D.10.38.01");
            class2.setText("D.10.38.02");
            class3.setText("D.10.38.03");
            class4.setText("D.10.38.04");
            class5.setText("D.10.38.05");
            String p1 = df.format(((list1.size()/10.0)*100)) + "%";
            String p2 = df.format(((list2.size()/10.0)*100)) + "%";
            String p3 = df.format(((list3.size()/10.0)*100)) + "%";
            String p4 = df.format(((list4.size()/10.0)*100)) + "%";
            String p5 = df.format(((list5.size()/10.0)*100)) + "%";
            percent1.setText(p1);
            percent2.setText(p2);
            percent3.setText(p3);
            percent4.setText(p4);
            percent5.setText(p5);
        }else if(Department.equals("QTKD")){
            try {
                list1 = TuitionReport.getuserlist("D.10.28.01",semester);
                list2 = TuitionReport.getuserlist("D.10.28.02",semester);
                list3 = TuitionReport.getuserlist("D.10.28.03",semester);
                list4 = TuitionReport.getuserlist("D.10.28.04",semester);
                list5 = TuitionReport.getuserlist("D.10.28.05",semester);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            class1.setText("D.10.28.01");
            class2.setText("D.10.28.02");
            class3.setText("D.10.28.03");
            class4.setText("D.10.28.04");
            class5.setText("D.10.28.05");
            String p1 = df.format(((list1.size()/10.0)*100)) + "%";
            String p2 = df.format(((list2.size()/10.0)*100)) + "%";
            String p3 = df.format(((list3.size()/10.0)*100)) + "%";
            String p4 = df.format(((list4.size()/10.0)*100)) + "%";
            String p5 = df.format(((list5.size()/10.0)*100)) + "%";
            percent1.setText(p1);
            percent2.setText(p2);
            percent3.setText(p3);
            percent4.setText(p4);
            percent5.setText(p5);

        }
    }
    // hàm lấy ra kì học
    private String getSemesterInSpinner(String str){
        String semester = String.valueOf(str.charAt(3));
        return semester;
    }
    private void Init() {
        // ánh xạ View
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        Department = sharedPreferences.getString("Manager"," ");
        buttonBack = findViewById(R.id.departmentSituation_back);
        pieChart = findViewById(R.id.departmentSituation_pieChar);

        class1 = findViewById(R.id.departmentSituation_class1);
        class2 = findViewById(R.id.departmentSituation_class2);
        class3 = findViewById(R.id.departmentSituation_class3);
        class4 = findViewById(R.id.departmentSituation_class4);
        class5 = findViewById(R.id.departmentSituation_class5);

        percent1 = findViewById(R.id.departmentSituation_percent1);
        percent2 = findViewById(R.id.departmentSituation_percent2);
        percent3 = findViewById(R.id.departmentSituation_percent3);
        percent4 = findViewById(R.id.departmentSituation_percent4);
        percent5 = findViewById(R.id.departmentSituation_percent5);

        textNameDepartment = findViewById(R.id.departmentSituation_textNameDepartment);
        textNameDepartment.setText(Department); // set giá trị để hiển thi tên khoa
        stringArrayList = new ArrayList<>();
        stringArrayList.add("Kì 1");
        stringArrayList.add("Kì 2");
        try {
            reportArrayList = TuitionReport.getuserlistDepartment(Department,getSemesterInSpinner(stringArrayList.get(0))); // lấy dữ liệu từ sql server
            studentArrayList = Student.getuserlistDepartment(Department);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        spinner = findViewById(R.id.departmentSituation_spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringArrayList);
        spinner.setAdapter(adapter);
    }

}