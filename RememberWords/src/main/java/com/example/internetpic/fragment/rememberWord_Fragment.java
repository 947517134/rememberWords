package com.example.internetpic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.pojo.User;
import com.example.internetpic.pojo.Word;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class rememberWord_Fragment extends Fragment {

    private String Str_json;
    private ArrayList<Word> arrWord = new ArrayList<>();

    private List<User> userList;
    private User user;
    private String username;
    private int index;
    private int wordnumber;

    private TextView tv_explain;
    private EditText et_word;
    private Button sumbitBtn,previousBtn,nextBtn,queryBtn;

    public static rememberWord_Fragment newRemember_Fragment(User user){
        rememberWord_Fragment fragment = new rememberWord_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",user.getUsername());//获取登录时的用户名
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rememberword,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doReadJson();
        doParseJson();
        tv_explain = view.findViewById(R.id.explain);
        et_word = view.findViewById(R.id.word);
        sumbitBtn = view.findViewById(R.id.sumbitBtn);
        previousBtn = view.findViewById(R.id.previousBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        queryBtn = view.findViewById(R.id.queryBtn);


        username = getArguments().getString("username");
        userList = LitePal.where("username=?", username).find(User.class);
        user = userList.get(0);//根据username获取User对象

        index = user.getWordnumber();//获取一开始的number
        wordnumber = user.getWordnumber();//获取一开始的number

        tv_explain.setText(arrWord.get(index).getExplain());//设置当前的explain

        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = et_word.getText().toString();
                String wordtrue = arrWord.get(index).getWord();
                if (word.equals(wordtrue)){
                    user.setWordnumber(index+1);
                    user.updateAll("username=?", username);

                    tv_explain.setText(arrWord.get(index+1).getExplain());
                    et_word.setText("");
                    index++;
                }else {
                    Toast.makeText(getActivity(), "回答错误！", Toast.LENGTH_SHORT).show();
                    et_word.setText("");
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index == 0 ? arrWord.size()-1 : index-1;
                tv_explain.setText(arrWord.get(index).getExplain());
                et_word.setText("");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index == arrWord.size()-1 ? 0 : index+1;
                tv_explain.setText(arrWord.get(index).getExplain());
                et_word.setText("");
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordtrue = arrWord.get(index).getWord();
                et_word.setText(wordtrue);
            }
        });

    }

    /*读取Json*/
    public void doReadJson(){
        try {
            InputStream is = getResources().getAssets().open("test.json");//根据文件名打开json文件，并存入输入流对象
            int length = is.available();//获取输入流字节长度
            byte[] buffer = new byte[length];//定义一个字节数组作为缓冲，长度为输入流的长度
            is.read(buffer);//将输入流中数据放进缓冲字节数组中
            Str_json = EncodingUtils.getString(buffer,"utf-8");//把字节数组中的数据放入字符串中，这样就把json文件读取成了字符串
            is.close();//切记，不要忘记关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*解析Json*/
    public void doParseJson() {
        if (Str_json == null) {// 判断用户是否读取了Json文件

        } else {
            try {
                JSONArray jsonArray = new JSONArray(Str_json);// 基于Str_json字符串创建Json对象数组
                for (int i = 0; i < jsonArray.length(); i++) {// 遍历Json数组
                    JSONObject jsonObject = jsonArray.getJSONObject(i);// 通过下标获取json数组元素——Json对象
                    // 对Json对象按键取值，其实我只需要image的网址就行了，也不需要组成对象2333，但是这只是个demo，组成对象是为了供后面使用
                    int id = jsonObject.getInt("id");
                    String word = jsonObject.getString("word");
                    String explain = jsonObject.getString("explain");
                    String sound = jsonObject.getString("sound");
                    String image = "https://fox.ftqq.com/"+jsonObject.getString("image");
                    Word wordObject = new Word(id, word, explain, sound, image);// 组成Word对象
                    arrWord.add(wordObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
