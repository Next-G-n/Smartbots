package com.SmartBots.controller;

import com.SmartBots.model.DAO.ConnectionUtil;
import com.SmartBots.model.DAO.hash;
import com.SmartBots.model.bean.Request;
import com.SmartBots.model.bean.User;

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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");

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

                case "Request":
                    Request(request,response);
                    break;
            }

        }

        catch (Exception exc) {
            exc.printStackTrace();

        }
    }

    private void Request(HttpServletRequest request, HttpServletResponse response)throws Exception {
       int companyId=Integer.parseInt(request.getParameter("company_Id"));
        int numberOfPositionsRequired=Integer.parseInt(request.getParameter("Number_Of_Positions_Required"));
        String qualificatonRequirements=request.getParameter("Qualification_Requirements");
        String dutyStaton=request.getParameter("Duty_Station");
        String department=request.getParameter("Department_Division");
        String briefDescription=request.getParameter("Brief_Description");
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
                briefDescription,dateUnix,status);
        System.out.println(qualificatonRequirements);
        String requestResult=connectionUtil.request(requestInfo,action);
        System.out.println(qualificatonRequirements);

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String action=request.getParameter("action");
        List<User> loginUser=connectionUtil.loginUser(email,password,action);
        if(!loginUser.isEmpty()){
            if(loginUser.get(0).getUserType().equals("Graduate")){
                session.setAttribute("UserInfo", loginUser);
                request.getRequestDispatcher("Home_Graduate.jsp").forward(request, response);
            }
        }
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
