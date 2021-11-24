package com.example.pantomime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassRegestration {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("MobileNo")
    @Expose
    public String MobileNo;
    @SerializedName("Password")
    @Expose
    public String Password;
    @SerializedName("IsActive")
    @Expose
    public boolean IsActive;

    public ClassRegestration(int Id,String MobileNo,String Password,boolean IsActive){
        this.Id=Id;
        this.MobileNo=MobileNo;
        this.Password=Password;
        this.IsActive=IsActive;

    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }
}
