package com.sam2019.model;

public class Paper {

    private String title;
    private String format;
    private String authors;
    private String contactAuthor;
    private String status;
    private String reviewer1;
    private String reviewer2;
    private String reviewer3;

     public Paper(String title, String format, String authors, String contactAuthor, String status, String reviewer1, String reviewr2, String reviewer3){
         this.title=title;
         this.format = format;
         this.authors = authors;
         this.contactAuthor = contactAuthor;
         this.status = status;
         this.reviewer1 = reviewer1;
         this.reviewer2 = reviewr2;
         this.reviewer3 = reviewer3;
     }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public String getAuthors() {
        return authors;
    }

    public String getContactAuthor() {
        return contactAuthor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setContactAuthor(String contactAuthor) {
        this.contactAuthor = contactAuthor;
    }

    public String getStatus() {
        return status;
    }

    public String getReviewer1() {
        return reviewer1;
    }

    public String getReviewer2() {
        return reviewer2;
    }

    public String getReviewer3() {
        return reviewer3;
    }
}
