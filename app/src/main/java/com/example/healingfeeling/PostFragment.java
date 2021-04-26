package com.example.healingfeeling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healingfeeling.databinding.FragmentPostBinding;
import com.example.healingfeeling.model.Post;
import com.example.healingfeeling.model.UserInfo;
import com.example.healingfeeling.ui.recommend.RecommendViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.ID;

public class PostFragment extends Fragment {

    private com.example.healingfeeling.ui.recommend.RecommendViewModel RecommendViewModel;

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;


    FragmentPostBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecommendViewModel =
                new ViewModelProvider(this).get(RecommendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        binding=FragmentPostBinding.bind(root);
        //final TextView textView = root.findViewById(R.id.text_post);
      /*  postViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

    /*    mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();
        if(flag){
            userInfo = new UserInfo(ID, PW, name, birth, gender, height, weight, disease, purpose)
            userValue = userInfo.toMap();
        }

        childUpdates.put("/User_info/" + ID, userValue);
        mDBReference.updateChildren(childUpdates);*/

        binding.checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeNewUser("1","노래","좋은","노래가 너무 좋아","ㅁㄴㅇㄹㅁㄴㅇ","행복");
            }
        });


        return root;
    }

    private void writeNewUser(String userId, String category, String title,String review,String image,String emotion) {

        Post post = new Post(category, title,review,image,emotion);
        mDBReference = FirebaseDatabase.getInstance().getReference();

        mDBReference.child("post").child(userId).setValue(post)
                .addOnSuccessListener(aVoid -> {
                    // Write was successful!
                    Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(getContext(), "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}