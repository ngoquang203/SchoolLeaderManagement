package com.example.schoolleadermanagement.DataManagement;

import com.example.schoolleadermanagement.SQLServerHelper.SQLServerManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logins {
    private String ID;
    private String Passwords;
    private String Position;
    private String Manager;

    public Logins(){};
    public Logins(String ID, String passwords, String position, String manager) {
        this.ID = ID;
        Passwords = passwords;
        Position = position;
        Manager = manager;
    }

    // hàm lấy tài khoản
    public static Logins getuserlist(String ID,String passWords) throws SQLException {
        Connection connection = SQLServerManagement.connectionSQLSever();
        Logins logins = new Logins();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Logins where ID = '" + ID + "' and Passwords = '" + passWords +"'";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            logins = new Logins(
                    rs.getString(1).trim(),
                    rs.getString(2).trim(),
                    rs.getString(3).trim(),
                    rs.getString(4).trim());// Đọc dữ liệu từ ResultSet)
        }
        statement.close();
        connection.close();// Đóng kết nối
        return logins;
    }

    // hàm ínsert logins
    public static void insertList(String ID, String passWords,String position,String manager) throws SQLException{
        Connection connection = SQLServerManagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "insert into Logins(ID,Passwords,Position,Manager) values ('" + ID + "','" + passWords +"',N'" + position + "',N'" + manager + "')\n"; // Câu lênh SQL Server thêm hàng mới trong bảng Logins
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPasswords() {
        return Passwords;
    }

    public void setPasswords(String passwords) {
        Passwords = passwords;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getManager() {
        return Manager;
    }

    public void setManager(String manager) {
        Manager = manager;
    }
}
