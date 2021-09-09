package com.bauerperception.itassetmanager.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
    static Connection conn;
    final static String IPADDRESS = "127.0.0.1";
    final static String USER = "root";
    final static String DATABASE_NAME = "itassetdb";
    final static String PASSWORD = "Buddy0325";
    //final static String HOST = "jdbc:mysql://" + IPADDRESS + "/" + DATABASE_NAME + "?useSSL=false";
    final static String HOST = "jdbc:mysql://" + IPADDRESS + "/" + DATABASE_NAME;

    public static void makeConn() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(HOST, USER, PASSWORD);
    }

    public static void closeConn() throws Exception {
        conn.close();
    }

    public static Connection getConn() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(HOST, USER, PASSWORD);
        return conn;
    }
}
