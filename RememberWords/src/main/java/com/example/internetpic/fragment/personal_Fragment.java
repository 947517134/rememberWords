package com.example.internetpic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.activity.LoginActivity;
import com.example.internetpic.activity.MainActivity;
import com.example.internetpic.activity.updatePwdActivity;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class personal_Fragment extends Fragment {

    private EditText et_username,et_phone,et_email;
    private Button updateDataBtn,toUpdatePwdBtn;
    private List<User> userList;

    public static personal_Fragment newPersonal_Fragment(User user){
        personal_Fragment fragment = new personal_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",user.getUsername());
        bundle.putString("phone",user.getPhone());
        bundle.putString("email",user.getEmail());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal,container,false);//参数：布局文件、容器、flase
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_username = view.findViewById(R.id.et_username);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);
        updateDataBtn = view.findViewById(R.id.updateDataBtn);
        toUpdatePwdBtn = view.findViewById(R.id.toUpdatePwdBtn);

        if (getArguments()!=null){
            et_username.setText(getArguments().getString("username"));
            et_phone.setText(getArguments().getString("phone"));
            et_email.setText(getArguments().getString("email"));
        }
        final String username = et_username.getText().toString();

        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = LitePal.where("username=?", username).find(User.class);
                User user = userList.get(0);
                user.setUsername(et_username.getText().toString());
                user.setPhone(et_phone.getText().toString());
                user.setEmail(et_email.getText().toString());
                user.updateAll("username=?", username);
                if (username.equals(et_username.getText().toString())){
                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "用户名已修改，请重新登录", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        toUpdatePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), updatePwdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });




    }
}
