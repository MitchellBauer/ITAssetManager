package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.controller.SettingsController;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConn {
    public static String applicationPath;
    static Connection conn;

    public static String ipaddress;
    public static String user;
    public static String databaseName;
    public static String password;

    public static void makeConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find MySQL database driver.");
        }
        getDatabaseConfig();
        String host = "jdbc:mysql://" + ipaddress + "/" + databaseName;
        try {
            conn = DriverManager.getConnection(host, user, password);
        } catch (SQLException e) {
            System.out.println("Database configuration is not correct, please change.");
        }
    }

    public static void closeConn() throws Exception {
        conn.close();
    }

    public static void getDatabaseConfig(){
        Properties properties = new Properties();
        String fileName = "dbconnection.properties";

        try {
            //System.out.println("Load file path: " + Objects.requireNonNull(DBConn.class.getResource("/com/bauerperception/itassetmanager/" + fileName)).getPath());
            //System.out.println("Load jar file path:" + DBConn.applicationPath);
            //InputStream inputStream = DBConn.class.getResourceAsStream("/com/bauerperception/itassetmanager/" + fileName);
            //InputStream inputStream = DBConn.class.getResourceAsStream(fileName);
            FileInputStream inputStream = new FileInputStream(DBConn.applicationPath + "dbconnection.properties");
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ipaddress = properties.getProperty("ipaddress");
        user = properties.getProperty("user");
        databaseName = properties.getProperty("databasename");
        password = properties.getProperty("password");
    }

    public static Connection getConn() {
        makeConn();
        return conn;
    }
}
