package com.yonusa.instaladores_plus.utilities;

public class Validations {

    private static final String regEx_email = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String regEx_password = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,16}$";

    public static boolean isValidEmail(String email) {

        Boolean isValid;

        if(email.isEmpty()) {
            isValid = false;
        } else {
            if(email.trim().matches(regEx_email)){
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return isValid;
    }

    public static boolean isValidPassword(String password) {

        Boolean isValid;

        if(password.isEmpty()){
            isValid = false;
        } else {
            if(password.trim().matches(regEx_password)){
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return isValid;
    }



}
