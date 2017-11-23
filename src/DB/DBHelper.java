package DB;

import java.io.*;
import java.sql.*;
import java.sql.CallableStatement;

public class DBHelper{
    Connection con;

    public DBHelper(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String userid="admin_user";
        String pwd="admin_user";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC Driver loading..");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            con=DriverManager.getConnection(url, userid, pwd);
            System.out.println("DB Connected..");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertMemberInfo(String ID, String password, String phone, boolean shop){

        try{
            String sql="insert Member;";
            Statement stmt= con.createStatement();
            stmt.execute(sql);
            System.out.println("Member information inserted..");
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }
//    public void insertMenu(Coupon coupon){
//
//    }
//    public void insertStore(Store store){
//
//    }
}