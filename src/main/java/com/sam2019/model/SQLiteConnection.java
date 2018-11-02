package com.sam2019.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteConnection {

    public static Connection connect() {
        Connection connection = null;

        try {
            // db parameters
            String url = "jdbc:sqlite:D:/SAM2019.db";
            // create a connection to the database
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void insertSubmitter(String userName, String email, String password) {
        String sql = "INSERT INTO Submitters(userName, email, password) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("User inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> validateRegistration(){
        String sql = "SELECT Username, Email FROM Submitters";
        List<String> usernames = new ArrayList<String>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                usernames.add(rs.getString("Username"));
                usernames.add(rs.getString("Email"));
            }
            return usernames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Boolean validateLogin(String username, String password){
        String sql = "SELECT Username, Password FROM Submitters WHERE Username = ?";
        String Username = "";
        String Password = "";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,username);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Username = rs.getString("Username");
                Password = rs.getString("Password");
            }
            if (Username.equals(username) && Password.equals(password))
                return true;
            else
                return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
