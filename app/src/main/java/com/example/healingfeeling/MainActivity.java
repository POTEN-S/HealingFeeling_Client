package com.example.healingfeeling;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.healingfeeling.ui.chatting.ChattingFragment;
import com.example.healingfeeling.ui.home.HomeFragment;
import com.example.healingfeeling.ui.post.PostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ChattingFragment chattingFragment;
    PostFragment postFragment;
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_chatting, R.id.navigation_home, R.id.navigation_post)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        chattingFragment = new ChattingFragment();
        postFragment = new PostFragment();
        homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,postFragment).commitAllowingStateLoss();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_chatting:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,chattingFragment).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.navigation_home:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,homeFragment).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.navigation_post:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,postFragment).commitAllowingStateLoss();
                        return true;
                    }

                    default: return false;




                }
            }
        });





    }

}