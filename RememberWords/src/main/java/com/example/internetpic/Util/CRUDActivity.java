package com.example.internetpic.Util;

import android.text.TextUtils;
import com.example.internetpic.pojo.User;
import org.litepal.LitePal;
import java.util.List;

public class CRUDActivity{

    public static int add(String username, String phone, String email, String password, String password_re) {
        List<User> users, users1, users2;
        users = LitePal.where("username=?", username).find(User.class);
        users1 = LitePal.where("phone=?", phone).find(User.class);
        users2 = LitePal.where("email=?", email).find(User.class);

        if (username.equals("")) {
            return 0;
        } else if (phone.equals("")) {
            return 1;
        } else if (!(Format_validation.isUsername(username))) {
            return 2;
        } else if (!(Format_validation.isPhone(phone))) {
            return 3;
        }  else if (!(email.equals(""))&&!(Format_validation.isEmail(email))) {
            return 4;
        } else if (email.equals("")&&TextUtils.isEmpty(password)) {
            return 5;
        } else if (!password.equals(password_re)) {
            return 6;
        } else if (users != null && users.size() != 0) {
            return 7;
        } else if (users1 != null && users1.size() != 0) {
            return 8;
        } else if (users2 != null && users2.size() != 0) {
            return 9;
        } else {
            User user = new User(username, phone, email, password, password_re);
            user.save();
        }
        return 20;
    }

    public static void update() {
    }

}
