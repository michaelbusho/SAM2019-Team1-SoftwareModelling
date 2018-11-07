package com.sam2019.model;


import java.io.*;
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

    public static List<String> validateRegistration() {
        String sql = "SELECT Username, Email FROM Submitters";
        List<String> usernames = new ArrayList<String>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

    public static Boolean validateLogin(String username, String password) {
        String sql = "SELECT Username, Password FROM Submitters WHERE Username = ?";
        String Username = "";
        String Password = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, username);
            //
            ResultSet rs = pstmt.executeQuery();

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

    public static Boolean insertPaper(String title, String format, String authors, String contactAuthor, String filePath) {
        // update sql
        String insertSQL = "INSERT INTO Papers(Title, Format, Version, Authors, Contact_Author, Paper) VALUES(?,?,?,?,?,?)";
        //if the new version checkbox is checked, update the row with the title (id)
        try (Connection conn = connect();

             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // set parameters
            pstmt.setString(1, title);
            pstmt.setString(2, format);
            pstmt.setInt(3, 0);
            pstmt.setString(4, authors);
            pstmt.setString(5, contactAuthor);
            pstmt.setBytes(6, readFile(filePath));

            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    public static Boolean updatePaper(String title, String format, String authors, String contactAuthor, String filePath) {
        // update sql
        String updateSQL = "UPDATE Papers "
                + "SET Format = ?, Version = ?, Authors = ?, Contact_Author = ?, Paper = ? "
                + "WHERE Title = ?";

        //if the new version checkbox is checked, update the row with the title (id)
        try (Connection conn = connect();

             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // set parameters
            pstmt.setString(1, format);
            pstmt.setInt(2, getVersion(title) + 1);
            pstmt.setString(3, authors);
            pstmt.setString(4, contactAuthor);

            pstmt.setBytes(5, readFile(filePath));
            pstmt.setString(6, title);

            pstmt.executeUpdate();
            System.out.println("Updated  the file in the BLOB column.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
            //close the file to be able to delete it
            fis.close();
            bos.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }

        return bos != null ? bos.toByteArray() : null;
    }

    private static int getVersion(String title) {
        String sql = "SELECT Version FROM Papers WHERE Title = ?";
        int version = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, title);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                version = rs.getInt("Version");
            }
            return version;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return version;

        }
    }

    public static List<String> getPapers(String author) {
        String sql = "SELECT Title FROM Papers WHERE Contact_Author = ?";
        List<String> Title = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, author);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Title.add(rs.getString("Title"));
            }
            return Title;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
