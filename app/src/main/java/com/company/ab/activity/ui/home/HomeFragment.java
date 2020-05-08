package com.company.ab.activity.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.company.ab.R;
import com.company.ab.activity.CalendarActivity;
import com.company.ab.database.ImageFeatures;
import com.company.ab.views.RoundedIconButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {

    private StorageReference mStorageRef;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myDatabaseRef = database.getReference("current/");

    private ImageView imageView1,imageView2;
    private EditText desciptionEditText1;
    private RoundedIconButton calendar_bt,image1_bt,image2_bt,submit_bt;
    private ProgressBar progressBar;
    private TextView lastDateTextView;

    private int last_date=-1;

    private Uri filePath1,filePath2;
    private String fileStoragePath1,fileStoragePath2;
    private boolean image1Request;

    private static final int PICK_FROM_GALLARY1 = 2;
    private static final int PICK_FROM_GALLARY2 = 3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        progressBar=root.findViewById(R.id.progress_bar);
        imageView1=root.findViewById(R.id.selectimage1_id);
        imageView2=root.findViewById(R.id.selectimage2_id);
        desciptionEditText1=root.findViewById(R.id.imageedit_desciption_id);
        submit_bt=root.findViewById(R.id.submit_bt_id);
        submit_bt.setButtonLabel("Submit");
        image1_bt=root.findViewById(R.id.image1_bt_id);
        image1_bt.setButtonLabel("Version 1");
        image2_bt=root.findViewById(R.id.image2_bt_id);
        image2_bt.setButtonLabel("Version 2");
        calendar_bt=root.findViewById(R.id.calendar_bt_id);
        calendar_bt.setButtonLabel("Enter Last Date");
        lastDateTextView=root.findViewById(R.id.date_id);
        image1Request=false;

        String lastdatestring=getActivity().getIntent().getStringExtra("date");
        if(lastdatestring!=null){
            last_date=getActivity().getIntent().getIntExtra("intdate",-1);
            lastDateTextView.setText(lastdatestring);
            lastDateTextView.setVisibility(View.VISIBLE);
        }

        image1_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1Request=true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLARY1);
            }
        });
        image2_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1Request=false;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLARY2);
            }
        });

        calendar_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), CalendarActivity.class));
            }
        });


        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=desciptionEditText1.getText().toString();
                if(title1.isEmpty()){
                    desciptionEditText1.setError("Please Enter Desciption");
                    return;
                }
                if(filePath1==null || filePath2==null){
                    Toast.makeText(getContext(),"Please Select image",Toast.LENGTH_LONG).show();
                    return;
                }

                if(last_date==-1){
                    Toast.makeText(getContext(),"Please Specify Date",Toast.LENGTH_LONG).show();
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
            if(requestCode==PICK_FROM_GALLARY1) {
                filePath1 = data.getData();
                Log.i("uri",filePath1.toString());
                imageView1.setImageURI(filePath1);
            }
            if(requestCode==PICK_FROM_GALLARY2){
                filePath2 = data.getData();
                Log.i("uri",filePath2.toString());
                imageView2.setImageURI(filePath2);
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
                            progressBar.setVisibility(View.INVISIBLE);
                            fileStoragePath2=uri.toString();
                            String ranuuid=UUID.randomUUID().toString();
                            myDatabaseRef.child(ranuuid).setValue(getImageProperties(ranuuid));
                            Toast.makeText(getContext(),"Testing is uploaded",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"Cannot Upload Image",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    private ImageFeatures getImageProperties(String uuid){
        ImageFeatures imageFeatures=new ImageFeatures();
        imageFeatures.setImageDesciption(desciptionEditText1.getText().toString());
        imageFeatures.setImageurl1(fileStoragePath1);
        imageFeatures.setImageurl2(fileStoragePath2);
        imageFeatures.setLastdate(last_date);
        imageFeatures.setUpVote(0);
        imageFeatures.setDownVote(0);
        imageFeatures.setUuid(uuid);
        imageFeatures.setaVote(0);
        imageFeatures.setbVote(0);

        List<String> list=new ArrayList<>();
        imageFeatures.setFeedback(list);
        return imageFeatures;
    }


}
