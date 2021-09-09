package com.example.healingfeeling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healingfeeling.databinding.ActivityMypageBinding;
import com.example.healingfeeling.model.User;
import com.example.healingfeeling.ui.Calendar.EventDecorator;
import com.example.healingfeeling.ui.Calendar.SaturdayDecorator;
import com.example.healingfeeling.ui.Calendar.SundayDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {
    private static final String TAG = "MypageActivity";

    ActivityMypageBinding binding;
    DatabaseReference myRef;
    TextView myname;
    ImageView imageView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public String fname=null;
    public String str=null;


    private SharedPreferences pref;

    TextView angry_text;
    TextView sad_text;
    TextView happy_text;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView2,textView3;
    public EditText contextEditText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        binding= DataBindingUtil.setContentView(this,R.layout.activity_mypage);



        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        findViewById(R.id.userDeleteButton).setOnClickListener(onClickListener);
        final TextView textView = findViewById(R.id.textView10);
        save_Btn=findViewById(R.id.save_Btn);
        del_Btn=findViewById(R.id.del_Btn);
        cha_Btn=findViewById(R.id.cha_Btn);
        textView2=findViewById(R.id.textView11);
        contextEditText=findViewById(R.id.contextEditText);




        happy_text = (TextView) findViewById(R.id.hyview);
        sad_text = (TextView) findViewById(R.id.sadview);
        angry_text = (TextView) findViewById(R.id.angryview);





        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull @NotNull MaterialCalendarView materialCalendarView, @NonNull @NotNull CalendarDay calendarDay, boolean b) {




                    save_Btn.setVisibility(View.VISIBLE);
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                int year = calendarDay.getYear();
                int month = calendarDay.getMonth() +1;
                int day = calendarDay.getDay();

                contextEditText.setText("");
                checkDay(year,month,day);

                textView.setText(String.format("%d년 %d월 %d일", year,month,day));
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str=contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

            }
        });




        materialCalendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator());

        //myname = (TextView) findViewById(R.id.myPageNickName);
        //imageView=findViewById(R.id.mypageActivity_imageview_profile);



        user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user!= null? user.getUid() : null;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        //DatabaseReference username = myRef.child(uid).child("userName");
        //DatabaseReference profile_image = myRef.child(uid).child("profileImageUrl");
        DatabaseReference mCondition_h = myRef.child(uid).child("happy_emotion");
        DatabaseReference mCondition_s = myRef.child(uid).child("sad_emotion");
        DatabaseReference mCondition_a = myRef.child(uid).child("angry_emotion");
        DatabaseReference mCondition = myRef.child(uid);




        /*profile_image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profile_url = snapshot.child("image").getValue(String.class);
               // Glide.with(MypageActivity.this).load(profile_url).apply(new RequestOptions().circleCrop())
                 //       .into(imageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                //myname.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
*/
        mCondition_h.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int happy = snapshot.getValue(Integer.class);
                happy_text.setText(happy + "번");



            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {


            }
        });



        mCondition_s.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int sad = snapshot.getValue(Integer.class);
                sad_text.setText(sad + "번");

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mCondition_a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int angry = snapshot.getValue(Integer.class);
                angry_text.setText(angry+ "번" );


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {


            }



        });




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                //데이터를 화면에 출력해 준다.
                Log.d(TAG, "Value is: " + map);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mCondition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int happy = snapshot.child("happy_emotion").getValue(Integer.class);
                int sad = snapshot.child("sad_emotion").getValue(Integer.class);
                int angry = snapshot.child("angry_emotion").getValue(Integer.class);

                Log.w(TAG, "   happy :   "+happy +"    sad :  "+ sad+ "   angry:   "+ angry);

                if(happy > sad && happy > angry){
                    materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, Collections.singleton(CalendarDay.today())));


                }else if(sad > happy && sad > angry){
                    materialCalendarView.addDecorator(new EventDecorator(Color.BLUE, Collections.singleton(CalendarDay.today())));


                }else if(angry > happy && angry > sad){
                    materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today())));

                }else
                    materialCalendarView.addDecorator(new EventDecorator(Color.GRAY, Collections.singleton(CalendarDay.today())));



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        materialCalendarView.state().edit().commit();












    }





    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logoutButton:
                    pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("id");
                    editor.remove("pw");
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
                case  R.id.userDeleteButton:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                    builder.setTitle("회원탈퇴").setMessage("정말 회원탈퇴를 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //yes 클릭
                            mAuth.getCurrentUser().delete();
                            deleteUserData(mAuth.getCurrentUser().getDisplayName());




                            myStartActivity(LoginActivity.class);

                        }
                    });

                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    break;


            }
        }
    };



    public void deleteUserData(final String userName){
        //get userID
        String uid = mAuth.getInstance().getCurrentUser().getUid();
        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot data: snapshot.getChildren()){
                        data.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void  checkDay(int cYear,int cMonth,int cDay){
        fname=""+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        try{
            fis=openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });
            if(textView2.getText()==null){
                textView2.setVisibility(View.INVISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }







}
