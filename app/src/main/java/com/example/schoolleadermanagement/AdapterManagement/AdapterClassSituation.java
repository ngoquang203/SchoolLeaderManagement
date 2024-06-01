package com.example.schoolleadermanagement.AdapterManagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.schoolleadermanagement.DataManagement.Student;
import com.example.schoolleadermanagement.DataManagement.TuitionReport;
import com.example.schoolleadermanagement.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdapterClassSituation extends BaseAdapter {
    Context context;
    ArrayList<Student> arrayList;
    ArrayList<TuitionReport> reportArrayList;
    LayoutInflater layoutInflater;
    String Classes;

    public AdapterClassSituation(Context context, ArrayList<Student> arrayList,ArrayList<TuitionReport> reportArrayList,String classes) {
        this.context = context;
        this.arrayList = arrayList;
        this.reportArrayList = reportArrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.Classes = classes;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_student,null);

        TextView IdStudent = convertView.findViewById(R.id.itemStudent_IdStudent);
        TextView tuition = convertView.findViewById(R.id.itemStudent_tuition);

        Student student = arrayList.get(position);
        if (student!=null){
            IdStudent.setText(student.getIdStudent());
            reportArrayList = new ArrayList<>();
            try {
                reportArrayList = TuitionReport.getuserlist(Classes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            boolean flag = true;
            for(int i = 0;i<reportArrayList.size();++i){
                if(student.getIdStudent().equals(reportArrayList.get(i).getIdStudent())){
                    tuition.setText("Đã nộp");
                    tuition.setTextColor(Color.GREEN);
                    flag = false;
                    break;
                }
            }
            if (flag){
                tuition.setText("Chưa nộp");
                tuition.setTextColor(Color.RED);
            }
        }
        return convertView;
    }
}
