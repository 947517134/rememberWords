package com.example.internetpic.Util;

import android.text.TextUtils;

public class Format_validation {


    public static boolean isUsername(String username) {
        String all = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        if (TextUtils.isEmpty(username)) {
            return false;
        } else {
            return username.matches(all);
        }

    }

    public static boolean isPhone(String mobiles) {
        String telRegex = "^((1[3,5,7,8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        return strEmail.matches(strPattern);

    }
}
