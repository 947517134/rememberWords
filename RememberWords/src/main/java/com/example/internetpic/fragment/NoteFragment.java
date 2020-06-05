package com.example.internetpic.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.internetpic.MyApplication;
import com.example.internetpic.R;
import com.example.internetpic.activity.LoginActivity;
import com.example.internetpic.activity.updatePwdActivity;
import com.example.internetpic.listview.NoteWordAdapter;
import com.example.internetpic.liteOrm.DBHelper;
import com.example.internetpic.liteOrm.liteOrmTable.CarData;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

public class NoteFragment extends Fragment {
    private EditText wordEdit;
    private TextView confrim2;
    private ListView listView;
    private String phone;

    public static NoteFragment newPersonal_Fragment(User user) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", user.getUsername());
        bundle.putString("phone", user.getPhone());
        bundle.putString("email", user.getEmail());
        fragment.setArguments(bundle);
        return fragment;
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);//参数：布局文件、容器、flase
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordEdit = view.findViewById(R.id.word2);
        listView = view.findViewById(R.id.listView);
        confrim2 = view.findViewById(R.id.confrim2);
        confrim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarData data = new CarData();
                data.setMobile(phone);
                String word = wordEdit.getText().toString().trim();
                if (word != null && !word.equals("")) {
                    data.setWord(word);
                    long insert = DBHelper.get().insert(data);
                    List<CarData> mobile = DBHelper.get().queryByWhere(CarData.class, "mobile", phone);
                    NoteWordAdapter adapter = new NoteWordAdapter(getActivity(), mobile);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "请输入单词内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getArguments() != null) {
            phone = getArguments().getString("phone");
//            et_username.setText(getArguments().getString("username"));
//            et_phone.setText(getArguments().getString("phone"));
//            et_email.setText(getArguments().getString("email"));
        }
        List<CarData> mobile = DBHelper.get().queryByWhere(CarData.class, "mobile", phone);
        NoteWordAdapter adapter = new NoteWordAdapter(getActivity(), mobile);
        listView.setAdapter(adapter);
    }
}
