package com.example.internetpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class updatePwdActivity extends AppCompatActivity {

    private EditText et_BeforePwd,et_AfterPwd;
    private Button changePwdBtn;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);


        et_BeforePwd = findViewById(R.id.et_BeforePwd);
        et_AfterPwd = findViewById(R.id.et_AfterPwd);
        changePwdBtn = findViewById(R.id.changePwdBtn);

        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");

        userList = LitePal.where("username=?", username).find(User.class);
        final User user = userList.get(0);

        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getPassword().equals(et_BeforePwd.getText().toString()) ){
                    if (user.getPassword().equals(et_AfterPwd.getText().toString())){
                        Toast.makeText(updatePwdActivity.this, "新密码不能和原密码相同！", Toast.LENGTH_SHORT).show();
                    }else {
                        user.setPassword(et_AfterPwd.getText().toString());
                        user.updateAll("username=?", username);
                        Toast.makeText(updatePwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(updatePwdActivity.this, "原密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
