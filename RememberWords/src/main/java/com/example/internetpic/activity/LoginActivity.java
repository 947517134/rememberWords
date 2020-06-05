package com.example.internetpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button bt1;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LitePal.initialize(LoginActivity.this);
        et_username = (EditText) findViewById(R.id.et_login);
        et_password = (EditText) findViewById(R.id.et_password);
        bt1 = (Button) findViewById(R.id.bt1);
        checkBox = findViewById(R.id.cbRemPwd);

        SharedPreferences sp1 = getSharedPreferences("login",MODE_PRIVATE);//获取应用级共享首选项
        String user = sp1.getString("username",null);//获取对应键的值，找不到该键则采用默认值（这里默认值设为null）
        String pwd = sp1.getString("password",null);

        et_username.setText(user);
        et_password.setText(pwd);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);//获取共享首选项对象，指定存储的文件名和存储方式
                    SharedPreferences.Editor editor = sp.edit();//获取该对象的Editor接口
                    editor.putString("username",et_username.getText().toString());//使用Editor来向创建（打开）的文件中写入数据
                    editor.putString("password",et_password.getText().toString());//同上
                    editor.commit();//写入完成后提交，不提交的话更改不会生效！
                }

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                List<User> list;

                if (et_username.getText().toString().equals("")|| et_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    list = LitePal.where("username=?", username).find(User.class);
                    if (list.size()!=0){
                        if (list.get(0).getUsername().equals(username) && list.get(0).getPassword().equals(password)) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username",username);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "账户或密码有错误", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "找不到此用户名，请先注册", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        //设置图片大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.user);
        drawable1.setBounds(0, 0, 65, 67);
        et_username.setCompoundDrawables(drawable1, null, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.password);
        drawable2.setBounds(0, 0, 65, 67);
        et_password.setCompoundDrawables(drawable2, null, null, null);
    }
}
