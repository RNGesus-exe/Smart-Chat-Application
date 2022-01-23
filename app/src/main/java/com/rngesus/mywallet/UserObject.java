package com.rngesus.mywallet;

public class UserObject {
    private String name;
    private String phone;

    public UserObject(String name,String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
