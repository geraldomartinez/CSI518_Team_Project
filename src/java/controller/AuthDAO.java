/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class AuthDAO {

    private static Connection conn; //Connection object
    //public static final String DB_URL = "jdbc:mysql://localhost:3306/lab3"; //Database URL
    //public static final String DB_USER = "root"; //Database username
    //public static final String DB_PW = ""; //Database password
    public static final String DB_URL = "jdbc:mysql://GreatDanes.db.6936824.hostedresource.com:3306/GreatDanes"; //Database URL
    public static final String DB_USER = "GreatDanes"; //Database username
    public static final String DB_PW = "Csi518!!"; //Database password
    
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver"; //Database driver

    /*Verifies a username/password combo*/
    public static int checkUserPass(String userName /*in, username to verify*/, String password /*in, password to verify*/) {
        Statement stmt = null; //SQL statement object
        ResultSet rs = null; //Result set object obtained from database
        String sql; //SQL statement string
        int id = -1; //User's unique ID in database 

        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) {//An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT `userID` FROM `user` WHERE `username`='" + userName + "' AND `password`='" + password + "' LIMIT 1";
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                id = rs.getInt("userID");
            }
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Clean-up
        try {
            rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) {
            //If it fails to close, just leave it.
        }

        return id;

    }

    public static User getUserById(int userID) {

        Statement stmt;
        ResultSet rs;
        String sql;
        User usr;

        String username = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        float checkingBal = 0.0f;
        float savingsBal = 0.0f;

        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM `user` JOIN `user_profile` ON `user_profile`.`userID`=`user`.`userID` WHERE `user`.`userID`='" + userID + "';";
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("username");
                password = rs.getString("password");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                checkingBal = rs.getFloat("checkingBal");
                savingsBal = rs.getFloat("savingsBal");
            }
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new User();
        }

        //Clean-up
        try {
            rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occured
            //Log the exception
            //If it fails to close, just leave it.
        }

        usr = new User(userID, username, password, firstName, lastName, checkingBal, savingsBal);
        return usr;
    }

    public static int enterNewUser(String username, String password) {

        Statement stmt;
        ResultSet rs;
        String sql;
        int newUserID = -1;

        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `user` (`username`,`password`) VALUES ('" + username + "','" + password + "');";
            stmt.executeUpdate(sql);

            //Get ID of newly created user
            sql = "SELECT `userID` FROM `user` WHERE `username`='" + username + "' AND `password`='" + password + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
                newUserID = Integer.parseInt(rs.getString("userID"));
            }
        } catch (SQLException | NumberFormatException ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newUserID;

    }

    public static boolean enterUserName(int userID, String firstName, String lastName) {

        Statement stmt;
        String sql;
        boolean insertSuccess = false;
        float randMin = 1000000.0f;
        float randMax = 9999999.0f;
        Random rand = new Random();

        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `user_profile` (`userID`,`firstName`,`lastName`,`checkingBal`,`savingsBal`) VALUES ('" + userID + "','" + firstName + "','" + lastName + "','"+(rand.nextFloat() * (randMax - randMin) + randMin)+"','"+(rand.nextFloat() * (randMax - randMin) + randMin)+"');";
        System.out.println("["+sql+"]");
            insertSuccess = stmt.executeUpdate(sql) > 0;
        } catch (SQLException | NumberFormatException ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return insertSuccess;
    }

    public static boolean isUserNameAvailable(String userName) {

        Statement stmt;
        ResultSet rs;
        String sql;
        String username = null;

        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT `username` FROM `user` WHERE `username`='" + userName + "';";
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("username");
            }
        } catch (Exception ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return username == null;
    }

    public static void DB_Close() throws Throwable {
        try { //Attempt to close the database connection
            if (conn != null) { //If the connection object is set
                conn.close(); //Close the connection object
            }
        } catch (SQLException ex) { //An error occured
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
