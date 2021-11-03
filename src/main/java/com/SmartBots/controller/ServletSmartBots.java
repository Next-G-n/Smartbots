package com.SmartBots.controller;

import com.SmartBots.model.DAO.ConnectionUtil;
import com.SmartBots.model.DAO.hash;
import com.SmartBots.model.bean.Companyinfo;
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
                case "Company Registration":
                    registerCompany(request,response);
                    break;
            }

        }

        catch (Exception exc) {
            exc.printStackTrace();

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


    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String action=request.getParameter("action");
        System.out.println(action);
        List<User> loginUser=connectionUtil.loginUser(email,password,action);
        if(!loginUser.isEmpty()){
            if(loginUser.get(0).getUserType().equals("Graduate")){
                session.setAttribute("UserInfo", loginUser);
                request.getRequestDispatcher("companyForm.jsp").forward(request, response);
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
