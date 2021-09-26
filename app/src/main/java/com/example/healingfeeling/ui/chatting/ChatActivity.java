package com.example.healingfeeling.ui.chatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healingfeeling.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    EditText et;
    ListView listView;

    private FadingTextView fadingTextView;

   

    ArrayList<MessageItem> messageItems=new ArrayList<>();
    ChatAdapter adapter;

    //Firebase Database 관리 객체참조변수
    FirebaseDatabase firebaseDatabase;

    //'chat'노드의 참조객체 참조변수
    DatabaseReference chatRef;

    String chat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView textView = (TextView)this.findViewById(R.id.textView);
//        textView.setSelected(true);


        //제목줄 제목글시를 닉네임으로(또는 채팅방)


        et=findViewById(R.id.et);
        listView=findViewById(R.id.listview);

        adapter=new ChatAdapter(messageItems,getLayoutInflater());
        listView.setAdapter(adapter);

        fadingTextView = (FadingTextView) findViewById(R.id.fading_textview);

        String[] happy_sentence = {"여러분들이 생각하는 행복은 무엇인가요?", "요즘 당신의 행복지수를 높여주는 것이 있나요? ",
                "올해가 가기전에 꼭 이루고 싶은것이 있나요?", "오늘기분과 어울리거나 떠오르는 노래가 있나요?",
        "행복을 의미하는 낱말들을 얘기해보세요."};
        String[] sad_sentence = {"누구나 각자의 고민이 있어요. 고민을 털어놔 보세요!","내가 슬플때 ___ 하면 힘날거같아!",
                "당신이 힘들때 나오는 버릇이 있다면 무엇인가요?", "일상이 지루해 질때 지루함에서 벗어나는 각자의 방법이 있나요?",
        "요즘 당신의 상태 메세지를 한줄로 적어본다면?"};
        String[] angry_sentence = {"최근에 가장 기분나쁘거나 화가 났던일이 있었나요? ","쓰레기통에 던져 버리고 싶은 기억이 있다면 무엇인가요?",
                "자신만의 분노를 해소하는 방법은?"};



        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        firebaseDatabase= FirebaseDatabase.getInstance();


        SharedPreferences sharedPreferences= getSharedPreferences("test", Context.MODE_PRIVATE);
        chat = sharedPreferences.getString("chat","");

        chatRef= firebaseDatabase.getReference().child(chat);

        Log.e("this ref is :   ",chat);

        if(chat.equals("sadchat")) {
            fadingTextView.setTexts(sad_sentence);
            fadingTextView.setTimeout(1, FadingTextView.MINUTES);
        }else if(chat.equals("happychat")){
            fadingTextView.setTexts(happy_sentence);
            fadingTextView.setTimeout(1, FadingTextView.MINUTES);
        }else if(chat.equals("angrychat")){
            fadingTextView.setTexts(angry_sentence);
            fadingTextView.setTimeout(1, FadingTextView.MINUTES);

        }





        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                MessageItem messageItem= dataSnapshot.getValue(MessageItem.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                messageItems.add(messageItem);

                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void clickSend(View view) {

        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        String nickName= G.nickName;
        String message= et.getText().toString();
        String pofileUrl= G.profileUri;

        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

        //firebase DB에 저장할 값(MessageItem객체) 설정
        MessageItem messageItem= new MessageItem(nickName,message,time,pofileUrl);
        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

        //그게 싫으면...다른 뷰가 포커스를 가지도록
        //즉, EditText를 감싼 Layout에게 포커스를 가지도록 속성을 추가!![[XML에]
    }

}