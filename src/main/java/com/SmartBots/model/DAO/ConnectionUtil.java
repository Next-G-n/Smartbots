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
        int Count=0;
        String[] pos=email.split("_");
        try {
            myConn = dataSource.getConnection();
            String sql;
            if (action.equals("CompanyAdmin")) {
                em="Admin";
                sql = "SELECT * FROM smartbots.user u left join smartbots.Aplication a on u.`User Id`=a.`User Id` where" +
                        " a.`Status`='Working'";
                myStmt = myConn.prepareStatement(sql);
            }
            else
            if (action.equals("Admin")) {

                em="Admin";
                sql = "SELECT * FROM smartbots.user u left join smartbots.Aplication a on u.`User Id`=a.`User Id` where" +
                        " a.`field`='" +pos[0]+"'"+
                        " and a.`Level`='" +password+"'"+
                        " and a.`Status`='Pending' ;";
                myStmt = myConn.prepareStatement(sql);
            } else {
                em="Users";

                sql = "select * from user where Email=?";
                myStmt = myConn.prepareStatement(sql);
                myStmt.setString(1, email);
            }

            System.out.println(myStmt);
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

                if (action.equals("Admin")) {
                    System.out.println("testing ");
                    Count=Count+1;
                    int position= Integer.parseInt(pos[1]);
                    login2 = new User(id, firstName, lastName, password,email,omang,contact ,gender, location,userType);
                    if (Count<=position){
                        login.add(login2);
                    }

                } else
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

    public String request(Request requestInfo, String action) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        String error = "Successful";
        try {
            myConn = dataSource.getConnection();
            String sql3;
            System.out.println(requestInfo.getQualification_Requirements());
            if (action.equals("Submit")) {
                sql3 = "INSERT INTO `smartbots`.`request` (`Company Id`,`Number Of Positions Required`, `Qualification Requirements`, `Duty Station`, `Department / Division`, `Brief Description`," +
                        " `Date Unix`, `Status`, `Field`, `level`)" +
                        " VALUES('"+requestInfo.getCompany_Id()+"',?,?,?,?,?,?,?)";
                myStmt = myConn.prepareStatement(sql3);


            }else if(action.equals("Edit")){
                sql3= "Update request set `Company Id`,`Number Of Positions Required`=?, `Qualification Requirements`=?, `Duty Station`=?, `Department / Division`=?, `Brief Description`=?, `Date Unix`=?,`Status`=? , `Field`=?, `level`=? where `Request Id`=?";
                myStmt = myConn.prepareStatement(sql3);
                myStmt.setInt(8, requestInfo.getRequest_Id());


            }

            myStmt.setInt(1, requestInfo.getNumber_Of_Positions_Required());
            myStmt.setString(2, requestInfo.getQualification_Requirements());
            myStmt.setString(3, requestInfo.getDuty_Station());
            myStmt.setString(4, requestInfo.getDepartment_Division());
            myStmt.setString(5, requestInfo.getBrief_Description());
            myStmt.setString(6, requestInfo.getDate_Unix());
            myStmt.setString(7, requestInfo.getStatus());
            myStmt.setString(8, requestInfo.getField());
            myStmt.setString(9, requestInfo.getLevel());

            myStmt.execute();

        } catch (Exception ex) {
            System.out.println("There is an error" + ex);
        }finally {
            close(myConn,myStmt,null);
        }
        System.out.println("error "+error);
        return error;
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
                    "`Physical Address`, `Telephone`, `user_Id`, `vat`, `city`) VALUES " +
                    "(?,?,?,?,?,?,?)";
            myStmt = myConn.prepareStatement(sql2);
            myStmt.setString(1, companyinfo.getCompanyName());
            myStmt.setString(2, companyinfo.getEmail());
            myStmt.setString(3, companyinfo.getSector());
            myStmt.setString(4, companyinfo.getRegistration_Number());
            myStmt.setString(5, companyinfo.getPhysical_address());
            myStmt.setString(6, companyinfo.getTel());
            myStmt.setInt(7, companyinfo.getUser_id());
            myStmt.setString(5, companyinfo.getVat());
            myStmt.setString(6, companyinfo.getCity());
            myStmt.execute();

        }finally {
            close(myConn,myStmt,null);
        }

        return error;

    }

    public String getTesting() throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        String test="There is Nothing";
        try {
            myConn = dataSource.getConnection();
            String sql2 = "select `Brief Description` from request where `Request Id`=4";
            myStmt = myConn.prepareStatement(sql2);


            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                test = myRS.getString("Brief Description");
            }

        } finally {
            close(myConn,myStmt,myRS);
        }
        return test;
    }

    public void submitApplication(Application application, String action) throws Exception{
        Connection myConn=null;
        PreparedStatement myStmt=null;
        String error="Successful";
        try {
            myConn = dataSource.getConnection();
            String sql2;
            if (action.equals("Registration")) {
                sql2 = "INSERT INTO `smartbots`.`Aplication` (`User Id`, `field`," +
                        " `Type Of Intern`," +
                        " `Major`, `Level`," +
                        " `Programme`, `Date of Completing`," +
                        " `School`, `Status`," +
                        " `Qualification`, `CV`, `disability`) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                myStmt = myConn.prepareStatement(sql2);
                myStmt.setInt(1, application.getUser_id());
                myStmt.setString(2, application.getField());
                myStmt.setString(3, application.getTypeOfIntern());
                myStmt.setString(4, application.getMajor());
                myStmt.setString(5, application.getLevel());
                myStmt.setString(6, application.getProgramme());
                myStmt.setString(7, application.getDateOfCompletion());
                myStmt.setString(8, application.getSchool());
                myStmt.setString(9, application.getStatus());
                myStmt.setString(10, application.getQualification());
                myStmt.setString(11, application.getCV());
                myStmt.setString(12, application.getDisability());
                myStmt.execute();
            }
        }finally {
                close(myConn,myStmt,null);
            }

    }

    public List<UserTracker> startTrack(int id, String field, String level) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        String test="There is Nothing";
        List<UserTracker> tracker= new ArrayList<>();
        int count=0;
        int position=0;
        try {
            myConn = dataSource.getConnection();
            String sql2 = "select * from Aplication where `Level`='"+level+"' and `field`='"+field+"' and not `Status`='working'";
            myStmt = myConn.prepareStatement(sql2);
            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                int User_id=myRS.getInt("User Id");
                count=count+1;
                if (User_id == id){
                    position=count;
                }

            }
           UserTracker tracker1 =new UserTracker(count,position);
            tracker.add(tracker1);

        } finally {
            close(myConn,myStmt,myRS);
        }
        return tracker;
    }

    public List<Application> getApplicationInfo(int user_id) throws Exception{

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        String test="There is Nothing";
        List<Application> app= new ArrayList<>();
        int count=1;
        int position=0;
        try {
            myConn = dataSource.getConnection();
            String sql2 = "select * from Aplication where `User Id`='"+user_id+"'";
            myStmt = myConn.prepareStatement(sql2);
            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                int User_id=myRS.getInt("User Id");
                int Appication_id=myRS.getInt("idAplication");
                String field=myRS.getString("field");
                String Type=myRS.getString("Type Of Intern");
                String Major=myRS.getString("Major");
                String Level=myRS.getString("Level");
                String Programme=myRS.getString("Programme");
                String Date=myRS.getString("Date of Completing");
                String School=myRS.getString("School");
                String Status=myRS.getString("Status");
                String Qualification=myRS.getString("Qualification");
                String CV=myRS.getString("CV");
                String disability=myRS.getString("disability");

                Application application =new Application(Appication_id,User_id,field,Type,Major,Level,Programme,Date,disability,School,Status,Qualification,CV);
                app.add(application);
            }


        } finally {
            close(myConn,myStmt,myRS);
        }
        return app;
    }

    public List<Companyinfo> getRequest() throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        List<Companyinfo> tracker= new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql2 = "SELECT * FROM smartbots.request r left join smartbots.company_information c on c.`Company Id`=r.`Company Id` where not `Number Of Positions Required`=0;";
            myStmt = myConn.prepareStatement(sql2);
            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                int companyId=myRS.getInt("Company Id");
                String CompanyName=myRS.getString("Company Name");
                int position=myRS.getInt("Number Of Positions Required");
                int Request_Id=myRS.getInt("Request Id");
                Companyinfo companyinfo=new Companyinfo(companyId,CompanyName,position,Request_Id);
                tracker.add(companyinfo);
            }

        } finally {
            close(myConn,myStmt,myRS);
        }
        return tracker;
    }

    public Companyinfo getCompanyInfo(int company_id) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        Companyinfo companyinfo = null;
        try {
            myConn = dataSource.getConnection();
            String sql2 = "SELECT * FROM smartbots.company_information where `Company Id`="+company_id;
            myStmt = myConn.prepareStatement(sql2);
            myRS = myStmt.executeQuery();

            while (myRS.next()) {
                int User_id=myRS.getInt("user_Id");
                String companyName= myRS.getString("Company Name");
                String city= myRS.getString("city");
                String email= myRS.getString("Company Email");
                String sector= myRS.getString("Sector");
                String vat= myRS.getString("vat");
                String registration_Number= myRS.getString("Organization Registration Number");
                String physical= myRS.getString("Physical Address");
                String tel= myRS.getString("Telephone");

                companyinfo=new Companyinfo(company_id,User_id
                        ,companyName,city,email,
                        sector,vat,registration_Number,
                        physical,tel);


            }

        }finally {
            close(myConn,myStmt,myRS);
        }
        return companyinfo;
    }

    public Request getRequestSp(int request_id) throws Exception {
        Connection myConn=null;
        PreparedStatement myStmt=null;
        ResultSet myRS=null;
        Request request=null;
        try {
            myConn = dataSource.getConnection();
            String sql="select * from smartbots.request where `Request Id`="+request_id;
            myStmt = myConn.prepareStatement(sql);
            myRS = myStmt.executeQuery();
            while (myRS.next()) {
                int companyId=myRS.getInt("Company Id");
                int numberOfPositionsRequired=myRS.getInt("Company Id");
                String qualificatonRequirements=myRS.getString("Qualification Requirements");
                String dutyStaton=myRS.getString("Duty Station");
                String department=myRS.getString("Department / Division");
                String briefDescription=myRS.getString("Brief Description");
                String field=myRS.getString("Field");
                String level=myRS.getString("level");
                String dateUnix=myRS.getString("Date Unix");
                String status=myRS.getString("Status");
                request = new Request(request_id,companyId,
                        numberOfPositionsRequired,qualificatonRequirements,
                        dutyStaton,department,
                        briefDescription,dateUnix,status,level,field);


            }

            } finally {
            close(myConn,myStmt,myRS);
        }

        return request;
    }

    public void Accept(AcceptDate acceptDate,int pos) throws Exception {
        Connection myConn=null;
        PreparedStatement myStmt=null;
        String error="Successful";
        try {
            myConn = dataSource.getConnection();
            String sql2;
            sql2 = "INSERT INTO `smartbots`.`AcceptedApplication` (`User_id`, `Requst_id`, `Start date`, `End date`, `status`) VALUES " +
                    "(?,?,?,?,?)";
            myStmt = myConn.prepareStatement(sql2);
            myStmt.setInt(1, acceptDate.getUser_id());
            myStmt.setInt(2, acceptDate.getRequest_id());
            myStmt.setString(3, acceptDate.getStart_date());
            myStmt.setString(4, acceptDate.getEnd_date());
            myStmt.setString(5, acceptDate.getStatus());
            myStmt.execute();

            sql2="UPDATE `smartbots`.`Aplication` SET  `Status` = '"+acceptDate.getStatus()+"' WHERE (`User Id` = '"+acceptDate.getUser_id()+"');";
            myStmt = myConn.prepareStatement(sql2);
            myStmt.execute();
            int new_pos=pos-1;

            sql2="UPDATE `smartbots`.`request` SET `Number Of Positions Required` = "+new_pos+" WHERE (`Request Id` = '"+acceptDate.getRequest_id()+"');;";
            myStmt = myConn.prepareStatement(sql2);
            myStmt.execute();

        }finally {
            close(myConn,myStmt,null);
        }
    }

    public List<Request> getMyRequest(int user_id) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;

        List<Request> tracker= new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql="SELECT * FROM smartbots.company_information where `user_Id`="+user_id;
            myStmt = myConn.prepareStatement(sql);
            myRS = myStmt.executeQuery();

            while (myRS.next()) {
                int companyId=myRS.getInt("Company Id");
                String sql2 = "SELECT * FROM smartbots.request where `Company Id`="+companyId;
                myStmt = myConn.prepareStatement(sql2);
                myRS = myStmt.executeQuery();
                while (myRS.next()) {
                    int request_id=myRS.getInt("Request Id");
                    int numberOfPositionsRequired=myRS.getInt("Company Id");
                    String qualificatonRequirements=myRS.getString("Qualification Requirements");
                    String dutyStaton=myRS.getString("Duty Station");
                    String department=myRS.getString("Department / Division");
                    String briefDescription=myRS.getString("Brief Description");
                    String field=myRS.getString("Field");
                    String level=myRS.getString("level");
                    String dateUnix=myRS.getString("Date Unix");
                    String status=myRS.getString("Status");
                    Request request = new Request(request_id,companyId,
                            numberOfPositionsRequired,qualificatonRequirements,
                            dutyStaton,department,
                            briefDescription,dateUnix,status,level,field);

                    tracker.add(request);
                }
            }


        } finally {
            close(myConn,myStmt,myRS);
        }
        return tracker;
    }

    public AcceptDate getAccepted(int request_id) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRS = null;
        AcceptDate acceptDate=null;
        try{
            myConn = dataSource.getConnection();
            String sql="SELECT * FROM smartbots.AcceptedApplication where `Requst_id`="+request_id;
            myStmt = myConn.prepareStatement(sql);
            myRS = myStmt.executeQuery();
            while (myRS.next()){
                int user_Id= myRS.getInt("User_id");
                int accp_id= myRS.getInt("Accept Appliaction");
                String end_Date=myRS.getString("End date");
                String Start_date=myRS.getString("Start date");
                String status=myRS.getString("Start date");
                acceptDate=new AcceptDate(accp_id,user_Id,request_id,Start_date,end_Date,status);

            }
        }finally {
            close(myConn,myStmt,myRS);
        }
        return acceptDate;
    }
}
