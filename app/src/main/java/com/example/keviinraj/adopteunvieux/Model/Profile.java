package com.example.keviinraj.adopteunvieux.Model;

import java.sql.Date;

import static android.R.attr.description;

/**
 * Created by keviinraj on 18/10/2018.
 */

public class Profile {

    private Integer id;
    private Boolean old;
    private String fname;
    private String lname;
    private String login;
    private String password;
    private Date birthdate;
    private Integer photo;
    private String description;
    private String location;
    private Integer correspondent;


    public Profile(Integer id, Boolean old, String fname, String lname, String login, String password, Date birthdate, Integer photo, String description, String location, Integer correspondent) {
        this.id = id;
        this.old = old;
        this.fname = fname;
        this.lname = lname;
        this.login = login;
        this.password = password;
        this.birthdate = birthdate;
        this.photo = photo;
        this.description = description;
        this.location = location;
        this.correspondent = correspondent;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getOld() {
        return old;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Integer getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCorrespondent() {
        return correspondent;
    }

    public void setOld(Boolean old) {
        this.old = old;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCorrespondent(Integer correspondent) {
        this.correspondent = correspondent;
    }
}
