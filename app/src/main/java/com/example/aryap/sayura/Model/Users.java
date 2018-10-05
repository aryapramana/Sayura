package com.example.aryap.sayura.Model;

public class Users {
    private String name;
    private String password;
    private String phone;

    public Users() {

    }

    public Users(String name, String password) {
        this.name = name;
        this.password = password;

        // at 4:49
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
