package com.sam2019.model;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteConnection {

    public static Connection connect() {
        Connection connection = null;

        try {
            // db parameters - Make this dynamic for app deployment
            String url = "jdbc:sqlite:/Users/mike/sqlite/SAM2019.db";
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

    public static User validateLogin(String username, String password) {
        String sql = "SELECT * FROM Submitters WHERE Username = ?";
        String Username = "";
        String Password = "";
        String Email = "";
        String Type = "";

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
                Email = rs.getString("Email");
                Type = rs.getString("Type");
            }

            if (Username.equals(username) && Password.equals(password)) {
                User currentUser = new User(Username,Email,Password,Type);
                return currentUser;
            }
            else
                return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Boolean insertPaper(String title, String format, String authors, String contactAuthor, InputStream file) {
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
            pstmt.setBytes(6, readFile(file));

            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    public static Boolean updatePaper(String title, String format, String authors, String contactAuthor, InputStream file) {
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

            pstmt.setBytes(5, readFile(file));
            pstmt.setString(6, title);

            pstmt.executeUpdate();
            System.out.println("Updated  the file in the BLOB column.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static byte[] readFile(InputStream file) {
        ByteArrayOutputStream bos = null;
        try {
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = file.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
            //close the file to be able to delete it
            file.close();
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

    public static List<String> getPCMS(String paperID) {

        String sql = "                SELECT Username\n" +
                "        FROM Submitters\n" +
                "        WHERE  Submitters.Username NOT IN ( SELECT reviewerName\n" +
                "                FROM Reviewers\n" +
                "                WHERE Reviewers.paperID == ?\n" +
                "        ) AND Submitters.Type = \"PCM\"";



        List<String> userNames = new ArrayList<String>();

        //int version = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, paperID);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                userNames.add(rs.getString("Username"));

            }
            
            return userNames;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }


    public static List<Paper> getPapersForReview(String Reviewer) {

        String  sql = " SELECT * \n" +
                "        FROM Papers\n" +
                "        INNER JOIN Reviewers ON Papers.paperID = Reviewers.paperID \n" +
                "        AND Reviewers.reviewerName = ? AND Reviewers.completed = false ";



        List<Paper> papers = new ArrayList<>();


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Reviewer);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                String id = rs.getString("paperID");
                String title = rs.getString("Title");
                String format = rs.getString("Format");
                String authors = rs.getString("Authors");
                String contactAuthor = rs.getString("Contact_Author");
                String Status = rs.getString("Status");

                Paper currentPaper = new Paper(id,title,format,authors,contactAuthor, Status);
                papers.add(currentPaper);
            }
            return papers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }



    public static boolean assignSubmitter(String reviewer, String paper) {

        // update sql
        String insertSQL = "INSERT INTO Reviewers(paperID, reviewerName) VALUES(?,?)";
        //if the new version checkbox is checked, update the row with the title (id)
        try (Connection conn = connect();

             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // set parameters
            pstmt.setString(1, paper);
            pstmt.setString(2, reviewer);


            pstmt.executeUpdate();
            System.out.println("Stored the reviewer assignment.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }


    }



    public static List<Paper> getPapers(String author, String type) {

        String sql = "";
        if(type.equals("Submitter")){
             sql = "SELECT * FROM Papers WHERE Contact_Author = ?";
        }
        else{
            sql = "SELECT * FROM Papers";
        }


        List<Paper> papers = new ArrayList<>();


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if(type.equals("Submitter")){
                // set the value
                pstmt.setString(1, author);
            }

            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                String id = rs.getString("paperID");
                String title = rs.getString("Title");
                String format = rs.getString("Format");
                String authors = rs.getString("Authors");
                String contactAuthor = rs.getString("Contact_Author");
                String Status = rs.getString("Status");

                Paper currentPaper = new Paper(id,title,format,authors,contactAuthor, Status);
                papers.add(currentPaper);
            }
            return papers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }





    public static Paper getPaper(String id) {
        String sql = "SELECT * FROM Papers WHERE paperID = ?";



        int version = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, id);
            //
            ResultSet rs = pstmt.executeQuery();
            Paper currentPaper = null;
            // loop through the result set
            while (rs.next()) {
                String paperID = rs.getString("paperID");
                String title = rs.getString("Title");
                String format = rs.getString("Format");
                String authors = rs.getString("Authors");
                String contactAuthor = rs.getString("Contact_Author");
                String Status = rs.getString("Status");

                 currentPaper = new Paper(paperID,title,format,authors,contactAuthor, Status);
            }

            return currentPaper ;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }




    public static Boolean insertReview( String paperID, String reviewer, String comment, String rating) {
        // update sql
        String insertSQL = "INSERT INTO Reviews(paperID, reviewer, comment, rating) VALUES(?,?,?,?)";
        //if the new version checkbox is checked, update the row with the title (id)
        try (Connection conn = connect();

             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // set parameters
            pstmt.setString(1, paperID);
            pstmt.setString(2, reviewer);
            pstmt.setString(3, comment);
            pstmt.setString(4, rating);

            pstmt.executeUpdate();
            System.out.println("Review stored  in the db.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }
    }


    public static List<String> getReviewersNames(String paperID) {

        String sql = "SELECT * FROM Reviewers WHERE paperID = ?";


        List<String> reviewerAssignments = new ArrayList<String>();


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paperID);
            // pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                String reviewerID = rs.getString("reviewerName");

                reviewerAssignments.add(reviewerID);
            }
            return reviewerAssignments;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static boolean pcmCompleteReview(String reviewer, String paperID) {

        // update sql
        String updateSQL = "UPDATE Reviewers "
                + "SET completed = ?"
                + "WHERE reviewerName = ? AND paperID = ?";

        //if the new version checkbox is checked, update the row with the title (id)
        try (Connection conn = connect();

             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // set parameters
            pstmt.setBoolean(1, true);
            pstmt.setString(2, reviewer);
            pstmt.setString(3, paperID);

            pstmt.executeUpdate();
            System.out.println("Updated  the file .");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }




    }


}
