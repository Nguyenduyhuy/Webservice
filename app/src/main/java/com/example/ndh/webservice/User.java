package com.example.ndh.webservice;

/**
 * Created by NDH on 4/23/2018.
 */

public class User {
    private String Name;
    private String Id;
    private boolean Sex;
    private String Email;
    private String Password;
    public User(String id,String name,  boolean sex) {
        Name = name;
        Id = id;
        Sex = sex;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }

    public String getName() {
        return Name;
    }

    public User(String name) {
        Name = name;
    }

    public User() {
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(String name, String email, String password) {

        Name = name;
        Email = email;
        Password = password;
    }



}
