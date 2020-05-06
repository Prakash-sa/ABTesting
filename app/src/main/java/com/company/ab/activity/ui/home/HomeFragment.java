package com.company.ab.activity.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.company.ab.R;
import com.company.ab.database.ImageFeatures;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {

    private StorageReference mStorageRef;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myDatabaseRef = database.getReference("current/");

    private ImageView imageView1,imageView2;
    private EditText editText1,editText2;
    private Button submit_bt;
    private ProgressBar progressBar;

    private Uri filePath1,filePath2;
    private String fileStoragePath1,fileStoragePath2;
    private boolean image1Request;

    private static final int PICK_FROM_GALLARY = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        progressBar=root.findViewById(R.id.progress_bar);
        imageView1=root.findViewById(R.id.image1_id);
        imageView2=root.findViewById(R.id.image2_id);
        editText1=root.findViewById(R.id.text1_id);
        editText2=root.findViewById(R.id.text2_id);
        submit_bt=root.findViewById(R.id.submit_bt_id);

        image1Request=false;


        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=editText1.getText().toString();
                String title2=editText2.getText().toString();
                if(title1.isEmpty()){
                    editText1.setError("Please Enter Name");
                    return;
                }
                if(title2.isEmpty()){
                    editText2.setError("Please Enter Name");
                    return;
                }
                if(filePath1==null || filePath2==null){
                    Toast.makeText(getContext(),"Please Select image",Toast.LENGTH_LONG).show();
                    return;
                }
                uploadImage1();

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {

            if(requestCode==PICK_FROM_GALLARY) {
                if(image1Request){
                    filePath1 = data.getData();
                    imageView1.setImageURI(filePath1);
                }
                else {
                    filePath2 = data.getData();
                    imageView2.setImageURI(filePath2);
                }
            }

        }
    }


    private void uploadImage1(){
        progressBar.setVisibility(View.VISIBLE);
        final String imageuri="images/"+ UUID.randomUUID().toString();
        final StorageReference tmpStorageRef=mStorageRef.child(imageuri);
        tmpStorageRef.putFile(filePath1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    tmpStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fileStoragePath1=uri.toString();
                            uploadImage2();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Cannot Upload Image",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    private void uploadImage2(){
        final String imageuri="images/"+ UUID.randomUUID().toString();
        final StorageReference tmpStorageRef=mStorageRef.child(imageuri);
        tmpStorageRef.putFile(filePath2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    tmpStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fileStoragePath2=uri.toString();
                            myDatabaseRef.child(UUID.randomUUID().toString()).setValue(getImageProperties());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Cannot Upload Image",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    private ImageFeatures getImageProperties(){
        ImageFeatures imageFeatures=new ImageFeatures();
        imageFeatures.setImagetext1(editText1.getText().toString());
        imageFeatures.setImagetext2(editText2.getText().toString());
        imageFeatures.setImageurl1(fileStoragePath1);
        imageFeatures.setImageurl2(fileStoragePath2);
        imageFeatures.setLastdate("20020");
        imageFeatures.setVote(0);
        return imageFeatures;
    }


}
