package com.example.rememberWords;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int[] arrPic = new int[]{R.drawable.img01,R.drawable.img02,R.drawable.img03};
    private int index;
    private float touchDownX,touchUpX;
    ImageView imageView;
    bitmapF bitmapF = new bitmapF();
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha_in);

        imageView.setAnimation(animation);
        imageView.setImageBitmap(changeImg(arrPic[index]));



        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchDownX = event.getX();
                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    touchUpX = event.getX();
                    if (touchUpX - touchDownX > 50) {
                        index = index == 0 ? arrPic.length - 1 : index - 1;
                        imageView.setAnimation(animation);
                        imageView.setImageBitmap(changeImg(arrPic[index]));

                    } else if (touchDownX - touchUpX > 50) {
                        index = index == arrPic.length - 1 ? 0 : index + 1;
                        imageView.setAnimation(animation);
                        imageView.setImageBitmap(changeImg(arrPic[index]));

                    }
                }
                return false;
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setImageResource(arrPic[index]);
                return true;
            }
        });
    }

    public Bitmap changeImg(int Resid){
        return bitmapF.BitmapMosaic(BitmapFactory.decodeResource(getResources(),Resid),40);
    }

}
