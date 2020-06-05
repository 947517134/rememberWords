package com.example.internetpic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.TextView;

import com.example.internetpic.R;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class statistics_Fragment extends Fragment {

    private TextView tv_username;
    private TextView tv_number;
    private List<User> userList;

    public static statistics_Fragment newStatistics_Fragment(User user){
        statistics_Fragment fragment = new statistics_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",user.getUsername());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = getArguments().getString("username");
        userList = LitePal.where("username=?", username).find(User.class);
        User user = userList.get(0);
        int number = user.getWordnumber();

        tv_username = view.findViewById(R.id.username_s);
        tv_number = view.findViewById(R.id.number_s);

        tv_username.setText("亲爱的"+username+"同学");
        tv_number.setText(String.valueOf(number));

    }
}
