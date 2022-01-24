package com.rngesus.mywallet.User;

public class UserObject {

    private String uid;
    private String name;
    private String phone;

    public UserObject(String uid,String name,String phone){
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
