package com.example.healingfeeling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healingfeeling.databinding.FragmentPostBinding;
import com.example.healingfeeling.model.Post;
import com.example.healingfeeling.model.UserInfo;
import com.example.healingfeeling.ui.recommend.RecommendViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.os.Build.ID;

public class PostFragment extends Fragment {

    private com.example.healingfeeling.ui.recommend.RecommendViewModel RecommendViewModel;

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;


    FragmentPostBinding binding;

    private Button btChoose;
    private Button btUpload;
    private ImageView ivPreview;
    private FirebaseAuth mAuth;
    private Uri filePath;
    String radioValue;
    View root;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecommendViewModel =
                new ViewModelProvider(this).get(RecommendViewModel.class);
        root = inflater.inflate(R.layout.fragment_post, container, false);
        mAuth = FirebaseAuth.getInstance();
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

                uploadFile();
            }}
            );

        binding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });


        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                binding.imageView4.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            //StorageReference storageRef = storage.getReferenceFromUrl("healingfeeling-9c1bf.appspot.com").child("images/" + filename);
            StorageReference storageRef = storage.getReference().child("postImage");
            //StorageReference storageRef = storage.child("profileImage").child();
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();


                            binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton rb=root.findViewById(checkedId);
                                    radioValue=rb.getText().toString();
                                }
                            });
                            if(binding.chipHappy.isChecked()){
                                Chip chip=root.findViewById(binding.chipHappy.getId());
                                chip.getText().toString();
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.toString(), binding.detailinput.toString(), storageRef.getDownloadUrl().toString(), chip.getText().toString());

                            }
                            else if(binding.chipSad.isChecked()){
                                Chip chip=root.findViewById(binding.chipSad.getId());
                                chip.getText().toString();
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.toString(), binding.detailinput.toString(), storageRef.getDownloadUrl().toString(), chip.getText().toString());

                            }
                            else{
                                Chip chip=root.findViewById(binding.chipAngry.getId());
                                chip.getText().toString();
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.toString(), binding.detailinput.toString(), storageRef.getDownloadUrl().toString(), chip.getText().toString());

                            }
                            //storageRef.getDownloadUrl();
                            //mDownloadImageUri = taskSnapshot.getUploadSessionUri();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }



    private void writeNewUser(String userId, String category, String title, String review, String image, String emotion) {

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