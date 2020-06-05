package com.example.internetpic.pojo;

import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {
    private int id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String password_qr;
    private int wordnumber;

    public User() {
    }

    public User(String username, String phone, String email, String password, String password_qr) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.password_qr = password_qr;
    }

    public User(int id, String username, String phone, String email, String password, String password_qr, int wordnumber) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.password_qr = password_qr;
        this.wordnumber = wordnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_qr() {
        return password_qr;
    }

    public void setPassword_qr(String password_qr) {
        this.password_qr = password_qr;
    }

    public int getWordnumber() {
        return wordnumber;
    }

    public void setWordnumber(int wordnumber) {
        this.wordnumber = wordnumber;
    }
}
