package com.example.healingfeeling;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.healingfeeling.ui.home.BookFragment;
import com.example.healingfeeling.ui.home.HomeFragment;
import com.example.healingfeeling.ui.home.PlaceFragment;
import com.example.healingfeeling.ui.home.SongFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //IslandActivity로부터 값 전달받기
        Intent intent = getIntent();
        String emotion = intent.getStringExtra("emotion");

        //Log.d("asdf",emotion);


        //SongFragment sf = new SongFragment();
        //BookFragment bf = new BookFragment();
        //PlaceFragment pf = new PlaceFragment();

       // Bundle sendbundle = new Bundle();
        //sendbundle.putString("emotion", emotion);

        //sf.setArguments(sendbundle);
        //bf.setArguments(sendbundle);
        //pf.setArguments(sendbundle);


        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startLoginActivity();
        }



        Toolbar tb = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(tb) ;

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_chatting, R.id.navigation_home, R.id.navigation_recommend)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


// 또는 view 를 이용하여 찾을수도 있다.


       // getSupportActionBar().setTitle("healingfeeling");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu) ;

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //등록하기 버튼
            navController.navigate(R.id.navigation_post);
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

}