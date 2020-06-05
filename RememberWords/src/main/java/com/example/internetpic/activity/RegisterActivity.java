package com.example.internetpic.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internetpic.R;
import com.example.internetpic.Util.CRUDActivity;

import org.litepal.LitePal;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username_re, et_password_re, et_password_qr_re, et_phone_re, et_mail_re;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LitePal.initialize(RegisterActivity.this);
        et_username_re = findViewById(R.id.et_useruame_re);
        et_password_re = findViewById(R.id.et_password_re);
        et_password_qr_re = findViewById(R.id.et_password_qr_re);
        et_phone_re = findViewById(R.id.et_phone_re);
        et_mail_re = findViewById(R.id.et_email_re);
        bt_register = findViewById(R.id.bt_Register);
        bt_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String username = et_username_re.getText().toString();
                String phone = et_phone_re.getText().toString();
                String email = et_mail_re.getText().toString();
                String password = et_password_re.getText().toString();
                String password_qr = et_password_qr_re.getText().toString();


                int code = CRUDActivity.add(username, phone, email, password, password_qr);
                switch (code) {
                    case 0:
                        Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(RegisterActivity.this, "用户名只能为字母、数字或汉字", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(RegisterActivity.this, "手机格式错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(RegisterActivity.this, "手机号已被绑定", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(RegisterActivity.this, "邮箱已被绑定", Toast.LENGTH_SHORT).show();
                        break;
                    case 20:
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });


        //设置图片大小
        //用户名
        Drawable username = getResources().getDrawable(R.drawable.user);
        username.setBounds(0, 0, 65, 67);
        et_username_re.setCompoundDrawables(username, null, null, null);


        //手机号
        Drawable phone = getResources().getDrawable(R.drawable.phone);
        phone.setBounds(0, 0, 65, 67);
        et_phone_re.setCompoundDrawables(phone, null, null, null);

        //邮箱
        Drawable email = getResources().getDrawable(R.drawable.email);
        email.setBounds(0, 0, 65, 67);
        et_mail_re.setCompoundDrawables(email, null, null, null);

        //密码
        Drawable password = getResources().getDrawable(R.drawable.password);
        password.setBounds(0, 0, 65, 67);
        et_password_re.setCompoundDrawables(password, null, null, null);

        //确认密码
        Drawable password_qr = getResources().getDrawable(R.drawable.password);
        password_qr.setBounds(0, 0, 65, 67);
        et_password_qr_re.setCompoundDrawables(password_qr, null, null, null);
    }

}
