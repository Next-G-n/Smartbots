package com.SmartBots.controller;

import com.SmartBots.model.DAO.ConnectionUtil;
import com.SmartBots.model.DAO.hash;
import com.SmartBots.model.bean.*;

import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@WebServlet(name = "ServletSmartBots", value = "/ServletSmartBots")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 14, // 10 MB
        maxFileSize = 1024 * 1024 * 1000000, // 1 GB
        maxRequestSize = 1024 * 1024 * 1000000)
public class ServletSmartBots extends HttpServlet {


    private ConnectionUtil connectionUtil;
    @Resource(name = "jdbc/SmartBots_aws")

    private DataSource dataSource;
    int id_user;
    public static int BUFFER_SIZE = 1024 * 100;
    public static final String UPLOAD_DIR = "resources";
    public static String fileName = null;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();

        try {
            // Create our StudentDBUtil.... and pass in the conn pool/dataSource
            connectionUtil = new ConnectionUtil(dataSource);
        }

        catch (Exception e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");

            System.out.println("this Command: "+theCommand);
            if (theCommand == null) {

                theCommand = "display_List";
            }
            switch (theCommand) {
                case "Registering User":
                    user_Registration(request,response);
                    break;
                case "Login":
                    login(request,response);
                    break;
                case "Company Registration":
                    registerCompany(request,response);
                    break;
                case "Application":
                    applicationGraduate(request,response);
                    break;
                case "getRequest":
                    getRequest(request,response);
                    break;
                case "Request":
                    Request(request,response);
                    break;
                case "acceptApplication":
                    AcceptRequest(request,response);
                    break;
            }

        }

        catch (Exception exc) {
            exc.printStackTrace();

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");

            System.out.println("this Command: "+theCommand);
            if (theCommand == null) {

                theCommand = "display_List";
            }
            switch (theCommand) {
                case "Registering User":
                    user_Registration(request,response);
                    break;
                case "Login":
                    login(request,response);
                    break;
                case "Company Registration":
                    registerCompany(request,response);
                    break;
                case "Application":
                    applicationGraduate(request,response);
                    break;
                case "getRequest":
                    getRequest(request,response);
                    break;
                case "Request":
                    Request(request,response);
                    break;
                case "acceptApplication":
                    AcceptRequest(request,response);
                    break;
            }

        }

        catch (Exception exc) {
            exc.printStackTrace();

        }
    }

    private void AcceptRequest(HttpServletRequest request, HttpServletResponse response) throws  Exception {
        int user_Id= Integer.parseInt(request.getParameter("user_id"));
        int request_id= Integer.parseInt(request.getParameter("Request_id"));
        String end_Date=request.getParameter("end_Date");
        String Start_date=request.getParameter("Start_date");
        int position= Integer.parseInt(request.getParameter("position"));
        String status="Working";

        AcceptDate acceptDate=new AcceptDate(0,user_Id,request_id,Start_date,end_Date,status);
        connectionUtil.Accept(acceptDate,position);
        getRequest(request,response);
    }

    private void Request(HttpServletRequest request, HttpServletResponse response)throws Exception {
        int companyId=Integer.parseInt(request.getParameter("company_Id"));
        int numberOfPositionsRequired=Integer.parseInt(request.getParameter("Number_Of_Positions_Required"));
        String qualificatonRequirements=request.getParameter("Qualification_Requirements");
        String dutyStaton=request.getParameter("Duty_Station");
        String department=request.getParameter("Department_Division");
        String briefDescription=request.getParameter("Brief_Description");
        String field=request.getParameter("field");
        String level=request.getParameter("level");
        String dateUnix="nothing";
        String status="request";
        String action=request.getParameter("action");
        int requestId=0;
        if (action.equals("Edit")) {
            requestId=Integer.parseInt(request.getParameter("Request_Id"));

        }
        Request requestInfo = new Request(requestId,companyId,
                numberOfPositionsRequired,qualificatonRequirements,
                dutyStaton,department,
                briefDescription,dateUnix,status,level,field);
        System.out.println(qualificatonRequirements);
        String requestResult=connectionUtil.request(requestInfo,action);
        System.out.println(qualificatonRequirements);

    }


    private void getRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("this worked");
        HttpSession session=request.getSession();
        int Request_id= Integer.parseInt(request.getParameter("Request_id"));
        int company_id= Integer.parseInt(request.getParameter("company_id"));
        String action=request.getParameter("action");
        Companyinfo getCompanyInfo=connectionUtil.getCompanyInfo(company_id);

        Request getReqest=connectionUtil.getRequestSp(Request_id);

        List<User> users=connectionUtil.loginUser(getReqest.getField()+"_"+getReqest.getNumber_Of_Positions_Required(),getReqest.getLevel(),action);

        session.setAttribute("specificCompany", getCompanyInfo);
        session.setAttribute("specificRequest", getReqest);
        session.setAttribute("SpecificUser", users);
        System.out.println("this worked");
        if(action.equals("CompanyAdmin")){
            AcceptDate acceptDate=connectionUtil.getAccepted(Request_id);
            session.setAttribute("SpecificDates", acceptDate);
            request.getRequestDispatcher("MYSC_Request_detail.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("MYSC_Request_detail.jsp").forward(request, response);
        }

    }

    private void applicationGraduate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String action=request.getParameter("action");
        int id= Integer.parseInt(request.getParameter("User_id"));
        String field=request.getParameter("field");
        String tpeOfIntern=request.getParameter("TypeOf");
        String major=request.getParameter("major");
        String level=request.getParameter("level");
        String pm=request.getParameter("pm");
        String dataOfComplection=request.getParameter("date of Completion");
        String disability=request.getParameter("Disability");
        String school=request.getParameter("School");
        String userName=request.getParameter("Name");

        System.out.println("plz");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ApplicationDate= String.valueOf(timestamp.getTime());

        String folderName6 = "Documents/Academic Qualification";
        String uploadPath6 = request.getServletContext().getRealPath("") + folderName6;
        File dir6 = new File(uploadPath6);
        if (!dir6.exists()) {
            dir6.mkdirs();
        }

        Part Qualification=request.getPart("Academic");
        InputStream hazardous_waste1 = Qualification.getInputStream();
        String fileName6=userName+"_Academic_Qualification"+ApplicationDate+".pdf";
        Files.copy(hazardous_waste1, Paths.get(uploadPath6 + File.separator + fileName6), StandardCopyOption.REPLACE_EXISTING);


        String folderName8 = "Documents/CV";
        String uploadPath8 = request.getServletContext().getRealPath("") + folderName8;
        File dir8 = new File(uploadPath8);
        if (!dir8.exists()) {
            dir8.mkdirs();
        }

        Part training_on_health_and_safety=request.getPart("CV");
        InputStream training_on_health_and_safety1 = training_on_health_and_safety.getInputStream();
        String fileName8=userName+"_CV_"+ApplicationDate+".pdf";
        Files.copy(training_on_health_and_safety1, Paths.get(uploadPath8 + File.separator + fileName8), StandardCopyOption.REPLACE_EXISTING);

        Application application=new Application(0,id,field,tpeOfIntern,major,level,pm,dataOfComplection,disability,school,"Pending",fileName6,fileName8);
         connectionUtil.submitApplication(application,action);

         getTrack(request,response,id,field,level);

    }

    private void getTrack(HttpServletRequest request, HttpServletResponse response, int id, String field, String level) throws Exception {
        HttpSession session=request.getSession();
        List<UserTracker> trackers=connectionUtil.startTrack(id,field,level);
        System.out.println("Well");
        if(!trackers.isEmpty()){
            System.out.println("greate");
            session.setAttribute("Track", trackers);
            request.getRequestDispatcher("track.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
        }


    }


    private void registerCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String action=request.getParameter("action");
        int id= Integer.parseInt(request.getParameter("User_id"));
        String companyName=request.getParameter("Company name");
        String City_Town_Village=request.getParameter("City");
        String Com_email=request.getParameter("Company_Email");
        String Sector=request.getParameter("Sector");
        String vat="None";
        String Registration_Number="None";
        if(Sector.equals("Private")){
            vat=request.getParameter("vat");
            Registration_Number=request.getParameter("Registration_Number");
        }

        String Physical_address=request.getParameter("Physical Address");
        String telephone=request.getParameter("telephone");
        String email=request.getParameter("Company_Email");

        Companyinfo companyinfo=new Companyinfo(0,id,companyName,
                City_Town_Village,Com_email,Sector,vat,Registration_Number,Physical_address,telephone);

        String error=connectionUtil.registerCompany(companyinfo,action);
        getTest(request,response);
        request.getRequestDispatcher("Home_Graduate.jsp").forward(request, response);


    }

    private void getTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String test= connectionUtil.getTesting();
        HttpSession session=request.getSession();
        String[] parts = test.split("•");
        session.setAttribute("Test", parts);
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String action=request.getParameter("action");
        System.out.println(action);
        List<User> loginUser=connectionUtil.loginUser(email,password,action);

        if(!loginUser.isEmpty()){
            System.out.println("User :"+loginUser.get(0).getUserType());
            if(loginUser.get(0).getUserType().equals("Graduate")){
                session.setAttribute("UserInfo", loginUser);

                List<Application> applications=connectionUtil.getApplicationInfo(loginUser.get(0).getUser_id());

                if(!applications.isEmpty()){
                    getTrack(request,response,loginUser.get(0).getUser_id(),applications.get(0).getField(),applications.get(0).getLevel());
                }else{
                    System.out.println("This is Null");
                    request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
                }

            } else if(loginUser.get(0).getUserType().equals("Company Representative")){
                session.setAttribute("UserInfo", loginUser);
                MyCompanyRequest(request,response,loginUser.get(0).getUser_id());
                request.getRequestDispatcher("companyForm.jsp").forward(request, response);
            }else if(loginUser.get(0).getUserType().equals("MYSC")){
                session.setAttribute("UserInfo", loginUser);
                System.out.println("Admin login");
                CompanyRequest(request,response);
                request.getRequestDispatcher("MYSC_Home.jsp").forward(request, response);

            }
        }
    }

    private void MyCompanyRequest(HttpServletRequest request, HttpServletResponse response, int user_id) throws Exception {
        HttpSession session=request.getSession();
        List<Request> requests=connectionUtil.getMyRequest(user_id);
        session.setAttribute("MyRequests", requests);

    }

    private void CompanyRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        List<Companyinfo> requests=connectionUtil.getRequest();
        System.out.println("This Id: "+requests.get(0).getRequest_id());
        session.setAttribute("Requests", requests);
    }

    private void user_Registration(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName=request.getParameter("first_name");
        String lastName=request.getParameter("last_name");
        String email=request.getParameter("email");
        String securePassword=null;
        String action=request.getParameter("action");
        int omang= Integer.parseInt(request.getParameter("Omang_code"));
        String userType=request.getParameter("userType");
        String phone_number= request.getParameter("phone_number");
        String location=request.getParameter("location");
        String gender=request.getParameter("gender");

        String password=request.getParameter("password");
        securePassword= hash.setHash(password);
        User userinfo=new User(firstName,lastName,securePassword,email,omang,phone_number,gender,location,userType);
        String result=connectionUtil.registerUser(userinfo,action);

        if(result.equals("Successful")){
            login(request,response);
        }


    }


}
