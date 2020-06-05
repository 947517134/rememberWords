package com.example.internetpic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class Sql extends AppCompatActivity {
    private Button bt_create, bt_add, bt_update, bt_query, bt_delete, bt_queryall, bt_deleteall;
    private TextView textView;
    private List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        LitePal.initialize(Sql.this);

        textView = (TextView) findViewById(R.id.tv);
        //创建数据库
        bt_create = (Button) findViewById(R.id.bt_create);
        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
            }
        });
        //添加数据test用
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User("王力宏", "17852837503", "123456@qq.com", "123", "123");
                User user1 = new User("周杰伦", "17852837503", "123@qq.com", "1234", "1234");
                User user2 = new User("123", "17669472106", "1231231@qq.com", "12345", "12345");
                user.save();
                user1.save();
                user2.save();
            }
        });
        //更新数据
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setPassword("10086");
                user.updateAll("username=?", "王力宏");
            }
        });

        //删除数据
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.deleteAll(User.class, "username=?", "王力宏");
            }
        });
        bt_deleteall = (Button) findViewById(R.id.bt_deleteall);
        bt_deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.deleteAll(User.class);
            }
        });

        //查询单个数据
        bt_query = (Button) findViewById(R.id.bt_query);
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                list = LitePal.findAll(User.class);
//                list = (List<User>) LitePal.select("username", "password").where("username=?", "王力宏").find(User.class);
                list = LitePal.where("username=?", "王力宏").find(User.class);
                disPlay();
            }
        });
        //查询所有数据
        bt_queryall = (Button) findViewById(R.id.bt_queryall);
        bt_queryall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = LitePal.findAll(User.class);
                disPlay();
            }
        });

    }


    //    将查询到的数据显示在TextView
    private void disPlay() {
        StringBuffer stringBuffer = new StringBuffer();
        for (User user : list) {
            stringBuffer.append(user.getId() + " " + user.getUsername() + "  " + user.getPhone() + "  " + user.getEmail() + "  " +user.getPassword() + "\n");
        }
        textView.setText(stringBuffer.toString());
    }


}
