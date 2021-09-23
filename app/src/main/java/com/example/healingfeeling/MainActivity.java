
package com.example.healingfeeling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.healingfeeling.ui.chatting.ChattingFragment;
import com.example.healingfeeling.ui.home.HomeFragment;
import com.example.healingfeeling.ui.home.SongFragment;
import com.example.healingfeeling.ui.recommend.RecommendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    String emotion;
    Menu menu;

    private HomeFragment menu1Fragment = new HomeFragment();
    private SongFragment songFragment = new SongFragment();
    private RecommendFragment menu2Fragment = new RecommendFragment();
    private ChattingFragment menu3Fragment = new ChattingFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startLoginActivity();

        }

        SharedPreferences sharedPreferences= this.getSharedPreferences("test", Context.MODE_PRIVATE);
        emotion = sharedPreferences.getString("emotion","");


        Log.v("Main2Activity","phone : " + emotion);


        BottomNavigationView navView = findViewById(R.id.nav_view);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, menu1Fragment).commitAllowingStateLoss();


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        transaction.replace(R.id.nav_host_fragment, menu1Fragment);
                        transaction.commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_chatting: {
                        transaction.replace(R.id.nav_host_fragment, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_recommend: {
                        transaction.replace(R.id.nav_host_fragment, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }

                }

                return true;
            }
        });

        //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,HomeFragment).commit();


        Toolbar tb = (Toolbar) findViewById(R.id.toolbar) ;
        tb.setBackgroundColor(Color.parseColor("#E5C1C5"));
        setSupportActionBar(tb) ;

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_chatting, R.id.navigation_home, R.id.navigation_recommend)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/


// 또는 view 를 이용하여 찾을수도 있다.


        // getSupportActionBar().setTitle("healingfeeling");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu) ;
        this.menu = menu;
        updateMenuTitles();

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //등록하기 버튼
            //navController.navigate(R.id.navigation_post);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new PostFragment()).commit();
            return true;
        }
        else if(item.getItemId()==R.id.action_island){
            startActivity(new Intent(this,IslandActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);



    }

    private void startLoginActivity() {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    private void updateMenuTitles() {
        MenuItem emotionMenuItem = menu.findItem(R.id.emotion_title);
        if(emotion.equals("행복")){
            emotionMenuItem.setTitle("『 Happy island 』");
        }else if(emotion.equals("슬픔")){
            emotionMenuItem.setTitle("『 Sad island 』");
        }else{
            emotionMenuItem.setTitle("『 Angry island 』");

        }


    }

}