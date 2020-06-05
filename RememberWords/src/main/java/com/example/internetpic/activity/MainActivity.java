package com.example.internetpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.Sql;
import com.example.internetpic.fragment.NoteFragment;
import com.example.internetpic.fragment.personal_Fragment;
import com.example.internetpic.fragment.rememberWord_Fragment;
import com.example.internetpic.fragment.statistics_Fragment;
import com.example.internetpic.listview.ListViewMusicActivity;
import com.example.internetpic.pojo.User;

import org.litepal.LitePal;

import java.util.List;

import com.example.internetpic.test.TestMusicActivity;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private rememberWord_Fragment rememberWord_Fragment;
    private statistics_Fragment statistics_Fragment;
    private personal_Fragment personal_Fragment;
    private NoteFragment noteFragment;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView nvDrawer;
    //    private TextView tv_username1, tv_email1;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.initialize(MainActivity.this);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        userList = LitePal.where("username=?", username).find(User.class);
        User u = userList.get(0);


        rememberWord_Fragment = rememberWord_Fragment.newRemember_Fragment(u);//实例化Fragment对象
        statistics_Fragment = statistics_Fragment.newStatistics_Fragment(u);
        personal_Fragment = personal_Fragment.newPersonal_Fragment(u);
        noteFragment = NoteFragment.newPersonal_Fragment(u);

        getFragmentManager().beginTransaction().add(R.id.frameLayout_fragment, personal_Fragment).commitAllowingStateLoss();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("标题");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.test_drawlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);

        nvDrawer = findViewById(R.id.nav_view);
        nvDrawer.setCheckedItem(R.id.item_inform);

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_note:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout_fragment, noteFragment)
                                .commitAllowingStateLoss();
                        break;
                    case R.id.item_inform:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout_fragment, personal_Fragment)
                                .commitAllowingStateLoss();
                        break;
                    case R.id.item_rememberWord:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout_fragment, rememberWord_Fragment)
                                .commitAllowingStateLoss();
                        break;
                    case R.id.item_statistics:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout_fragment, statistics_Fragment)
                                .commitAllowingStateLoss();
                        break;
                    case R.id.item_img:
                        Intent intent = new Intent(MainActivity.this, PicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item_music:
                        Intent intent1 = new Intent(MainActivity.this, TestMusicActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_youdao:
                        Intent intent2 = new Intent(MainActivity.this, youdao_WebView.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_zz:
                        Toast.makeText(MainActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_gx:
                        Toast.makeText(MainActivity.this, "为梦想奋斗的每一个人", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
