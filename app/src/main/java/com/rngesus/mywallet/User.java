package com.rngesus.mywallet;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class User {
    private String Name;
    private boolean Sign_In;
    private String PhoneNumber;
    private String mImageUrl;
    private String password;


    public User() {
    }

    public User(String name, boolean sign_In, String phoneNumber, String mImageUrl, String password) {
        Name = name;
        Sign_In = sign_In;
        PhoneNumber = phoneNumber;
        this.mImageUrl = mImageUrl;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSign_In() {
        return Sign_In;
    }

    public void setSign_In(boolean sign_In) {
        Sign_In = sign_In;

    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
