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
import android.widget.ArrayAdapter;
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
import com.example.healingfeeling.ui.home.RecyclerAdapter;
import com.example.healingfeeling.ui.recommend.RecommendViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> arrayTitle=new ArrayList<>();

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



        getData();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=root.findViewById(checkedId);
                radioValue=rb.getText().toString();
                Log.d("cateradioValue",radioValue);
            }
        });
        binding.checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadImg();
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
            StorageReference storageRef = storage.getReferenceFromUrl("gs://healingfeeling-9c1bf.appspot.com").child("post/" + filename);
            //StorageReference storageRef = storage.getReference().child("postimage");
            //StorageReference storageRef = storage.child("profileImage").child();
            //올라가거라...
            UploadTask uploadTask = storageRef.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri mDownloadImageUri = task.getResult();
                        Log.d(TAG + "DOWN", mDownloadImageUri + "");
                        //디비에넣기전 이전 아이디는 디비에서삭제
                        ArrayList<String> aa=new ArrayList<>();
                        aa.add("stupid");
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
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),mDownloadImageUri.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        else if(binding.chipSad.isChecked()){
                            Chip chip=root.findViewById(binding.chipSad.getId());
                            chip.getText().toString();
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),mDownloadImageUri.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        else{
                            Chip chip=root.findViewById(binding.chipAngry.getId());
                            chip.getText().toString();
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),mDownloadImageUri.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        //storageRef.getDownloadUrl();
                        //mDownloadImageUri = taskSnapshot.getUploadSessionUri();

                        startActivity(new Intent(getContext(),MainActivity.class));
                        progressDialog.dismiss();

                    } else {
                        // Handle failures
                        Toast.makeText(getContext(), "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            ArrayList<String> aa=new ArrayList<>();
                            aa.add("stupid");
                            //Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
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
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                        binding.subtitleinput.getText().toString(),, chip.getText().toString(),aa,0);

                            }
                            else if(binding.chipSad.isChecked()){
                                Chip chip=root.findViewById(binding.chipSad.getId());
                                chip.getText().toString();
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                        binding.subtitleinput.getText().toString(),storageRef., chip.getText().toString(),aa,0);

                            }
                            else{
                                Chip chip=root.findViewById(binding.chipAngry.getId());
                                chip.getText().toString();
                                writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                        binding.subtitleinput.getText().toString(),storageRef.getDownloadUrl().toString(), chip.getText().toString(),aa,0);

                            }
                            //storageRef.getDownloadUrl();
                            //mDownloadImageUri = taskSnapshot.getUploadSessionUri();

                            startActivity(new Intent(getContext(),MainActivity.class));
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
                    });*/
        } else {
            Toast.makeText(getContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImg()
    {
        try {
            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            StorageReference riversRef = storageRef.child("ghj");
            UploadTask uploadTask = riversRef.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "업로드 성공", Toast.LENGTH_SHORT).show();

                        //파이어베이스에 데이터베이스 업로드
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUrl = task.getResult();

                        ArrayList<String> aa=new ArrayList<>();
                        aa.add("stupid");
                        //Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();

                        if(binding.chipHappy.isChecked()){
                            Chip chip=root.findViewById(binding.chipHappy.getId());
                            chip.getText().toString();
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),downloadUrl.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        else if(binding.chipSad.isChecked()){
                            Chip chip=root.findViewById(binding.chipSad.getId());
                            chip.getText().toString();
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),downloadUrl.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        else{
                            Chip chip=root.findViewById(binding.chipAngry.getId());
                            chip.getText().toString();
                            writeNewUser(mAuth.getCurrentUser().getUid(), radioValue, binding.titleinput.getText().toString(),
                                    binding.subtitleinput.getText().toString(),downloadUrl.toString(), chip.getText().toString(),aa,0,Double.valueOf(binding.inputRatings.getText().toString()));

                        }
                        //storageRef.getDownloadUrl();
                        //mDownloadImageUri = taskSnapshot.getUploadSessionUri();

                        startActivity(new Intent(getContext(),MainActivity.class));

                        //image 라는 테이블에 json 형태로 담긴다.
                        //database.getReference().child("Profile").setValue(imageDTO);
                        //  .push()  :  데이터가 쌓인다.


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }catch (NullPointerException e)
        {
            Toast.makeText(getContext(), "이미지 선택 안함", Toast.LENGTH_SHORT).show();
        }
    }





    private void writeNewUser(String userId, String category, String title,String subtitle, String image, String emotion,ArrayList<String> favorite,int register,Double ratings) {
        Log.d("cate",category);
        Post post = new Post(category,title,subtitle,image,emotion,favorite,register,ratings);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        String key = mDBReference.child("post").push().getKey();
        mDBReference.child("post").child(userId).child(key).setValue(post)
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

        mDBReference.child("postList").child(key).setValue(post)
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


        mDBReference.child(emotion).child(key).setValue(post)
                .addOnSuccessListener(aVoid -> {
                    // Write was successful!
                    // Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(getContext(), "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        //평점 데이터 만들기
        mDBReference.child("score").child(category).child(userId).child(title).setValue(ratings)
                .addOnSuccessListener(aVoid -> {
                    // Write was successful!
                    // Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(getContext(), "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void getData() {
        // 임의의 데이터입니다.
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference().child("postList"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayTitle.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Post data = snapshot.getValue(Post.class); // 만들어뒀던 Data 객체에 데이터를 담는다.


                        arrayTitle.add(data.title);

                }
                Log.d("titlearray",arrayTitle.toString());
                binding.titleinput.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, arrayTitle));
                // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("SongFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });



    }


}