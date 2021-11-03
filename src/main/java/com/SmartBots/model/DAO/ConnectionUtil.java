package com.SmartBots.model.DAO;

import com.SmartBots.model.bean.*;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionUtil {
    private final DataSource dataSource;


    //constructor
    public ConnectionUtil(DataSource theDataSource) {
        dataSource=theDataSource;

    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {

            if(myRs !=null) {
                myRs.close();
            }
            if (myStmt !=null) {

                myStmt.close();
            }

            if (myConn !=null) {
                myConn.close();//*does not really close it...just put
            }
        }catch(Exception exc) {

            exc.printStackTrace();
        }
    }

    public String registerUser(User userReg,String action) throws Exception{
        Connection myConn=null;
        PreparedStatement myStmt=null;
        String error="Successful";
        try {
            myConn = dataSource.getConnection();
            String sql2;
            if(action.equals("Registration")){
                sql2= "INSERT INTO `smartbots`.`user` (`First Name`, `Last Name`, `Phone Number`, `Email`, `Location`, `Gender`, `Omang`, `User Type`, `Password`)" +
                        " VALUES(?,?,?,?,?,?,?,?,?)";
                myStmt = myConn.prepareStatement(sql2);
                myStmt.setString(1, userReg.getFirst_Name());
                myStmt.setString(2, userReg.getLast_name());
                myStmt.setString(3, userReg.getPhone_number());
                myStmt.setString(4, userReg.getEmail());
                myStmt.setString(5, userReg.getLocation());
                myStmt.setString(6, userReg.getGender());
                myStmt.setInt(7, userReg.getOmang());
                myStmt.setString(8, userReg.getUserType());
                myStmt.setString(9, userReg.getPassword());
                myStmt.execute();
            }else if(action.equals("Editing_Offers")){
                sql2= "Update user set `First Name`=?, `Last Name`=?, `Email`=?, `User Type`=?, `Omang`=?, `Contact`=?,`Location`=?" +
                        ", `Add Roles`=? where `User Id`=?";
                myStmt = myConn.prepareStatement(sql2);
                myStmt.execute();
            } else if(action.equals("Editing_Client")){
                sql2= "Update user set `First Name`=?, `Last Name`=?, `Email`=?, `User Type`=?, `Omang`=?, `Contact`=?,`Location`=? where `User Id`=?";
                myStmt = myConn.prepareStatement(sql2);
                myStmt.execute();
            }


        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("This is error: "+e);
            String ErrorSql= String.valueOf(e);
            if(ErrorSql.contains("Email_UNIQUE")){
                error ="Error Email";
            }

            error="You have "+e;
            System.out.println("This "+error);
        } finally {
            close(myConn,myStmt,null);
        }
        System.out.println("error "+error);
        return error;

    }



    public List<User> loginUser(String email, String password,String action) throws Exception{
        List<User> login=new ArrayList<>();
        Connection myConn=null;
        PreparedStatement myStmt=null;
        ResultSet myRS=null;
        String em=null;
        try {
            myConn = dataSource.getConnection();
            String sql;
            if (action.equals("Admin")) {
                System.out.println("thiss this");
                em="Admin";
                sql = "select * from user where not `User Type`='Client'";
                myStmt = myConn.prepareStatement(sql);
            } else {
                em="Users";

                sql = "select * from user where Email=?";
                myStmt = myConn.prepareStatement(sql);
                myStmt.setString(1, email);
            }


            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                int id = myRS.getInt("User Id");
                String userType = myRS.getString("User Type");
                String firstName = myRS.getString("First Name");
                String lastName = myRS.getString("Last Name");
                email = myRS.getString("Email");
                int omang = myRS.getInt("Omang");
                String contact = myRS.getString("Phone Number");
                String gender = myRS.getString("Gender");
                String location = myRS.getString("Location");
                String PasswordOG = myRS.getString("Password");

                User login2;


                    if(password.equals("None")){
                        login2 = new User(id, firstName, lastName, email, userType,omang,password , contact, location, "just");
                        login.add(login2);
                    }else {
                        boolean matched = hash.validatePassword(password, PasswordOG);
                        login2 = new User(id, firstName, lastName, password,email,omang,contact ,gender, location,userType);
                        login.add(login2);
                        if (!matched) {
                            login.clear();
                        }
                    }



            }
        }
        finally {
            close(myConn,myStmt,myRS);
        }

        return login;

    }


    public String registerCompany(Companyinfo companyinfo, String action) throws  Exception{
        Connection myConn=null;
        PreparedStatement myStmt=null;
        String error="Successful";
        try {
            myConn = dataSource.getConnection();
            String sql2;
            sql2= "INSERT INTO `smartbots`.`company_information` (`Company Name`," +
                    " `Company Email`, `Sector`," +
                    " `Organization Registration Number`, " +
                    "`Physical Address`, `Telephone`) VALUES " +
                    "(?,?,?,?,?,?)";
            myStmt = myConn.prepareStatement(sql2);
            myStmt.setString(1, companyinfo.getCompanyName());
            myStmt.setString(2, companyinfo.getEmail());
            myStmt.setString(3, companyinfo.getSector());
            myStmt.setString(4, companyinfo.getRegistration_Number());
            myStmt.setString(5, companyinfo.getPhysical_address());
            myStmt.setString(6, companyinfo.getTel());
            myStmt.execute();

        }finally {
            close(myConn,myStmt,null);
        }

        return error;

    }
}
