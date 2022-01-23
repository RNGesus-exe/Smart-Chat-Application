package com.rngesus.mywallet;

public class StudentUser {
    private String Name;
    private boolean Sign_In;
    private String PhoneNumber;
    private String password;


    public StudentUser() {
    }

    public StudentUser(String name, boolean sign_In, String phoneNumber, String password) {
        Name = name;
        Sign_In = sign_In;
        PhoneNumber = phoneNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
