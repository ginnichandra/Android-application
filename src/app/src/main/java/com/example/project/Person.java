package com.example.project;

public class Person {
    private int id;
    private String username;
    private String password;


    public Person() { }

    public void setId(int id) {
        this.id = id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
