package com.sam2019.model;

import java.util.List;
import java.util.ArrayList;
public class Paper {

    private String id;
    private String title;
    private String format;
    private String authors;
    private String contactAuthor;
    private String status;
    private List<String> submitters = null;

     public Paper(String id, String title, String format, String authors, String contactAuthor, String status){
         this.id = id;
         this.title=title;
         this.format = format;
         this.authors = authors;
         this.contactAuthor = contactAuthor;
         this.status = status;

     }


    public void setSubmitters(List<String> submitters) {
        this.submitters = submitters;
    }

    public List<String> getSubmitters() {
        return this.submitters;
    }

    public String getId() {
        return id;
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


}
