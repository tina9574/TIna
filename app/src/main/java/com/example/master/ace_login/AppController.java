package com.example.master.ace_login;

public class AppController {
    public boolean logedin=false;
    public int hr;


    private static AppController instance = new AppController();
    public static AppController getInstance() {
        return instance;
    }
}