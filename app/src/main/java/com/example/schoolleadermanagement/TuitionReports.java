package com.example.schoolleadermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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

public class TuitionReports extends AppCompatActivity {
    // tạo biến
    private TextView sumStudent,submitted,noSubmitted,cntt,tcnh,qtkd;
    private ImageButton buttonBack;
    private Spinner spinner;

    private ArrayList<Student> studentArrayList;
    private ArrayList<TuitionReport> reportsArrayList;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;
    private double sumCNTT,sumTCNH,sumQTKD;
    private double sumCNTTSubmit,sumTCNHSubmit,sumQTKDSubmit;
    private DecimalFormat df = new DecimalFormat("#.##");
    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_reports);
        Init(); // hàm khởi tạo giá trị
        clickBackPage(); // hàm quay về trang chủ
        clickSpiner(); // hàm thay đổi báo cáo khi chọn spinner
        setDataPieChart(); // hàm xét giá trị cho Pie chart
    }


    private void setDataPieChart() {
        // set data cho pie chart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Long.valueOf(reportsArrayList.size()),"Đã nộp"));
        entries.add(new PieEntry(Long.valueOf(studentArrayList.size() - reportsArrayList.size()),"Chưa nộp"));

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

    private void clickSpiner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    reportsArrayList = TuitionReport.getuserlistSchool(getSemesterInSpinner(stringArrayList.get(position)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                submitted.setText(String.valueOf(reportsArrayList.size()));
                noSubmitted.setText(String.valueOf(studentArrayList.size() - reportsArrayList.size()));
                setDataStudentSubmitNumber();
                setTextPerCentDepartment();
                setDataPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // hàm quay về trang chủ
    private void clickBackPage() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuitionReports.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    // hàm lấy ra kì học
    private String getSemesterInSpinner(String str){
        String semester = String.valueOf(str.charAt(3));
        return semester;
    }


    // hàm tính số lượng sinh viên các khoa
    private void setDataStudentNumber(){
        sumCNTT = 0;
        sumQTKD = 0;
        sumTCNH = 0;
        for (int i = 0;i<studentArrayList.size();++i){
            if(studentArrayList.get(i).getDepartment().equals("CNTT")) ++sumCNTT;
            else if(studentArrayList.get(i).getDepartment().equals("TCNH")) ++sumTCNH;
            else if(studentArrayList.get(i).getDepartment().equals("QTKD")) ++sumQTKD;
        }
        Log.e("DATA",String.valueOf(sumQTKD));
    }

    // hàm tính số lượng sinh viên đã đóng học phí
    private void setDataStudentSubmitNumber(){
        sumCNTTSubmit = 0;
        sumTCNHSubmit = 0;
        sumQTKDSubmit = 0;
        for(int i = 0;i<reportsArrayList.size();++i){
            if(reportsArrayList.get(i).getDepartment().equals("CNTT")) ++sumCNTTSubmit;
            else if(reportsArrayList.get(i).getDepartment().equals("TCNH")) ++sumTCNHSubmit;
            else if(reportsArrayList.get(i).getDepartment().equals("QTKD")) ++sumQTKDSubmit;

        }Log.e("DATA",String.valueOf(sumQTKDSubmit));

    }
    // hiển thị phân chăm tỉ lệ sinh viên của các khoa đã đóng
    private void setTextPerCentDepartment(){
        cntt.setText(df.format(((sumCNTTSubmit/sumCNTT)*100)) + "%" );
        tcnh.setText(df.format(((sumTCNHSubmit/sumTCNH)*100)) + "%" );
        qtkd.setText(df.format(((sumQTKDSubmit/sumQTKD)*100)) + "%" );
    }
    private void Init() {
        // ánh xạ View
        spinner = findViewById(R.id.tuitionReport_spinner);
        sumStudent = findViewById(R.id.tuitionReport_sumStudent);
        submitted = findViewById(R.id.tuitionReport_submitted);
        noSubmitted = findViewById(R.id.tuitionReport_noSubmitted);
        cntt = findViewById(R.id.tuitionReport_cntt);
        tcnh = findViewById(R.id.tuitionReport_tcnh);
        qtkd = findViewById(R.id.tuitionReport_qtkd);
        buttonBack = findViewById(R.id.tuitionReport_back);
        pieChart = findViewById(R.id.tuitionReport_pieChar);

        stringArrayList = new ArrayList<>();
        stringArrayList.add("Kì 1");
        stringArrayList.add("Kì 2");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringArrayList);
        spinner.setAdapter(adapter);
        try {
            studentArrayList = Student.getuserlist();
            reportsArrayList = TuitionReport.getuserlistSchool(getSemesterInSpinner(stringArrayList.get(0)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sumStudent.setText(String.valueOf(studentArrayList.size()));
        submitted.setText(String.valueOf(reportsArrayList.size()));
        noSubmitted.setText(String.valueOf(studentArrayList.size() - reportsArrayList.size()));

        setDataStudentNumber(); // hàm tính tổng sinh sinh đang có của các khoa
        setDataStudentSubmitNumber(); // hàm tính tổng sinh viên đã đóng học phí của các khoa
        setTextPerCentDepartment(); // hiển thị phân chăm tỉ lệ sinh viên của các khoa đã đóng
    }
}