package com.example.internetpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.internetpic.R;
import com.example.internetpic.Sql;

public class BeforeMainActivity extends AppCompatActivity {

    private Button toLogin,toReg,test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_main);
        toLogin = findViewById(R.id.toLogin);
        toReg = findViewById(R.id.toReg);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeforeMainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeforeMainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
//        test=findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BeforeMainActivity.this, Sql.class);
//                startActivity(intent);
//            }
//        });
    }
}
