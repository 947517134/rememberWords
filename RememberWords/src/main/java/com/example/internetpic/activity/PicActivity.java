package com.example.internetpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.internetpic.R;
import com.example.internetpic.pojo.Word;
import com.example.internetpic.dictionaryTransform;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class PicActivity extends AppCompatActivity {

    private ArrayList<String> arrPicPath = new ArrayList<>();//list存储图片URL
    private String Str_json;//全局变量存储json文件转换来的字符串
    private int index = 0;//List下标（索引）
    private float touchDownX,touchUpX;//按下、抬起时的X坐标
    ImageView imageView;//这个就不要写了吧
    Animation animation;//存储动画资源



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);

        imageView = findViewById(R.id.imageview);//ID获取image view

        animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha_in);//加载本地动画资源文件

        imageView.setAnimation(animation);//给image view设置动画

        doReadJson();//自定义方法，从本地读取Json文件
        doParseJson();//自定义方法，解析Json文件

        RequestOptions options = new RequestOptions();//实例化一个option对象，并设置属性
            options.centerCrop()//设置居中
                    .transform(new dictionaryTransform())//调用transform，实例化dictionaryTransform类调用自定义方法BitmapMosaic实现打码
                    .placeholder(R.drawable.img_load)//设置加载时显示的图片（占位）
                    .error(R.drawable.img_load)//失败时显示的图片
                    .fallback(R.drawable.img_load);//反馈图片

        final RequestBuilder<Drawable> requestBuilder =//定义一个RequestBuilder<Drawable>对象
                Glide.with(this)//实例化一个Glide对象
                        .asDrawable()//设置对象类型为Drawable
                        .apply(options);//应用options的设置

        final RequestBuilder<Drawable> requestBuilderwithout =//同上，但是这个对象不设置option，用以设置无打码的图片
                Glide.with(this)                       //其实严谨点应该设置两个option
                        .asDrawable();

        requestBuilder.clone()//清除
                .load(arrPicPath.get(index))//根据索引找到list中对应URL，load方法加载网络图片
                .into(imageView);//把图片放进image view，注意：此时使用的是设置过option的对象，即打码的图片

//        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png").into(imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {//为image view设置触摸监听事件
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//event.getAction()获取当前事件，利用常量判断该事件是否为按下事件
                    touchDownX = event.getX();//获取按下时的坐标，并存储
                    return false;//返回false表示按下事件没有被处理完，下面的监听事件才可以继续处理该按下事件
                } else if (event.getAction() == MotionEvent.ACTION_UP) {//同上，判断是否抬起
                    touchUpX = event.getX();//获取抬起时坐标，储存
                    if (touchUpX - touchDownX > 50) {//判断按下抬起坐标间隔，并定义为一次从左到右滑动
                        Random r = new Random();//定义并实例化一个随机数对象
                        index = index == 0 ? arrPicPath.size() - 1 : r.nextInt(arrPicPath.size());//三位运算符，判断index是否等于0，
                        // 若等于0则使index等于List的最后一个元素，若不等于0则使index等于生成的（0-list元素个数）一个随机数
                        //其实这一步逻辑上有错，因为一开始设置为有顺序的循环显示图片，所以需要判断左右滑动和List的下标防止越界
                        //改为随机后可直接删除左右滑动判断，和下标判断，直接随机数设置下标即可
                        requestBuilder.clone()//清除
                                .load(arrPicPath.get(index))//加载随机后的图片
                                .into(imageView);//设置进image view，同上，注意：此时依然设置的是打码图片


                    } else if (touchDownX - touchUpX > 50) {//同上，不再赘述
                        Random r = new Random();
                        index = index == arrPicPath.size() - 1 ? 0 : r.nextInt(arrPicPath.size());
                        requestBuilder.clone()
                                .load(arrPicPath.get(index))
                                .into(imageView);

                    }
                }
                return false;//注意！返回false表示抬起事件没有处理完，由于我下面的监听使用的是长按事件，所以上面两个是false还是true没有影响
                //如果想使用单击事件（onClick）则按下事件要返回false
            }
        });

//        Glide.with(this)
//                .load(R.drawable.img01)
//                .apply(options)
//                .into(imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {//给image view设置长按事件
                requestBuilderwithout.clone()//注意：此时使用的是没有设置option的对象，即加载的图片是无码的
                        .load(arrPicPath.get(index))
                        .into(imageView);
                return true;//返回true，表示长按事件处理完毕
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
                    Word wordObject = new Word(id, word, explain, sound, image);// 组成Word对象
                    arrPicPath.add(wordObject.getImage());//获取对象中的image的值，并添加到List中
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
