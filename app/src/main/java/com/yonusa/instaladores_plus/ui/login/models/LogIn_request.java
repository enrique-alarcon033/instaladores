package com.yonusa.instaladores_plus.ui.login.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogIn_request {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

  /*  public LogIn_request(){
    }

    public LogIn_request(String email, String password) {
        this.email = email;
        this.password = password;
    }*/

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

}
