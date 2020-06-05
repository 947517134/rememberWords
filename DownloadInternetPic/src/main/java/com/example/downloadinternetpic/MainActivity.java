package com.example.downloadinternetpic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String Str_json;
    private ArrayList<String> arrPicPath = new ArrayList<>();//list存储图片URL
    private int index;
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReadJson();//自定义方法，从本地读取Json文件
                doParseJson();//自定义方法，解析Json文件
                down d = new down();
                d.start();
                Toast.makeText(MainActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class down extends Thread{
        @Override
        public void run() {
            InputStream is = null;//初始化输入流
            BufferedInputStream bis = null;//初始化缓冲输入流
            FileOutputStream fos = null;//初始化输出流
            for (index = 0;index<arrPicPath.size();index++) {
                try {
                    String urlPath = arrPicPath.get(index);//定义网络图片URL
                    URL url = new URL(urlPath);//将字符串格式的URL转换成URL类型
                    URLConnection con = url.openConnection();//打开一个到该URL的连接
                    is = con.getInputStream();//获取输入流，用于读取/写入
                    bis = new BufferedInputStream(is);//将输入流转化成缓冲输入流（提高效率）

                    String state = Environment.getExternalStorageState();//获取sd卡状态
                    if (state.equals(Environment.MEDIA_MOUNTED)) {//判断sd是否可用
                        String path = Environment.getExternalStorageDirectory() +"/"+index+".png";//定义文件存储路径（包含了文件名和格式）
                        fos = new FileOutputStream(path);//使用输出流写入文件到指定路径
                        int i;
                        while ((i = bis.read()) != -1) {//使用while循环写入文件，缓冲输入流每次读取单个字节存储到变量i
                            fos.write(i);//输出流尽心写入
                        }
                        Toast.makeText(MainActivity.this, "已下载"+index, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
            Toast.makeText(this, "请先读取Json文件！", Toast.LENGTH_SHORT).show();//没有就弹出提示
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

                    arrPicPath.add(image);//获取对象中的image的值，并添加到List中
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
