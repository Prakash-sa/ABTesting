package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.ab.R;
import com.company.ab.constants.Constants;
import com.company.ab.database.ImageFeatures;
import com.company.ab.database.ProfileFeatures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageViewActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myDatabaseRef = database.getReference();

    List<String>submittedUuid=new ArrayList<>();
    private ImageView imageView1,imageView2;
    private LinearLayout linearLayout1,linearLayout2;
    private TextView submit_text,imageDesciption;
    private ProfileFeatures profileFeatures;
    private ImageButton upVotebt,downVotebt,submitbt;
    private EditText feedbackedittext;
    private TextView upvoteTextView,downVoteTextView;

    private int vote=0;

    private ImageFeatures imageFeatures;

    private ProgressDialog progressDialog;

    private String uuid;
    private int selected=-1;
    private boolean isAble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);


        submit_text=findViewById(R.id.submit_flag_id);
        imageView1=findViewById(R.id.image_button1_id);
        imageView2=findViewById(R.id.image_button2_id);
        linearLayout1=findViewById(R.id.lineraLayout1_id);
        linearLayout2=findViewById(R.id.lineraLayout2_id);
        imageDesciption=findViewById(R.id.image_decription_text_id);
        upVotebt=findViewById(R.id.upvote_id);
        downVotebt=findViewById(R.id.downvote_id);
        submitbt=findViewById(R.id.submit_bt_id);
        feedbackedittext=findViewById(R.id.feedback_id);
        upvoteTextView=findViewById(R.id.text_up_id);
        downVoteTextView=findViewById(R.id.text_down_id);

        user = FirebaseAuth.getInstance().getCurrentUser();

        isAble=true;
        selected=0;


        Bundle b=getIntent().getExtras();
        imageFeatures= (ImageFeatures) b.getSerializable("object");

        uuid=imageFeatures.getUuid();
        submit_text.setVisibility(View.INVISIBLE);
        Glide.with(this).load(imageFeatures.getImageurl1()).into(imageView1);
        Glide.with(this).load(imageFeatures.getImageurl2()).into(imageView2);
        imageDesciption.setText(imageFeatures.getImageDesciption());

        upvoteTextView.setText(imageFeatures.getUpVote() +"");
        downVoteTextView.setText(imageFeatures.getDownVote()+"");

        myDatabaseRef.child("users").child(user.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postSnapshot) {
                profileFeatures = postSnapshot.getValue(ProfileFeatures.class);
                if(profileFeatures!=null) {
                    submittedUuid = profileFeatures.getUuid();
                    Log.i("contain uuid",submittedUuid.size()+" "+uuid);

                    if (submittedUuid.contains(uuid)) {
                        isAble = false;
                        submit_text.setVisibility(View.VISIBLE);
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        upVotebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upVotebt.setClickable(false);
                downVotebt.setClickable(true);
                vote=1;
                upvoteTextView.setText(imageFeatures.getUpVote()+1+"");
            }
        });

        downVotebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upVotebt.setClickable(true);
                downVotebt.setClickable(false);
                vote=-1;
                downVoteTextView.setText(imageFeatures.getDownVote()+1+"");
            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAble){
                    selected=1;
                    Toast.makeText(ImageViewActivity.this,"Image A is selected",Toast.LENGTH_LONG).show();
                }
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAble){
                    selected=2;
                    Toast.makeText(ImageViewActivity.this,"Image B is selected",Toast.LENGTH_LONG).show();
                }
            }
        });

        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected==0){
                    Toast.makeText(getApplicationContext(),"Please select A or B.",Toast.LENGTH_LONG).show();
                    return;
                }
                uploadAB();
                uploadProfile();
            }
        });
    }

    private void uploadAB(){
        ImageFeatures newImageFeatures=imageFeatures;
        if(selected==1){
            newImageFeatures.setbVote(imageFeatures.getbVote());
            newImageFeatures.setaVote(imageFeatures.getaVote()+1);
        }
        else {
            newImageFeatures.setbVote(imageFeatures.getbVote()+1);
            newImageFeatures.setaVote(imageFeatures.getaVote());
        }
        List<String>newfeesback;
        newfeesback=imageFeatures.getFeedback();
        String feedbackstring=feedbackedittext.getText().toString();
        if(feedbackstring!=null)newfeesback.add(feedbackstring);
        newImageFeatures.setFeedback(newfeesback);

        if(vote==1){
            newImageFeatures.setUpVote(imageFeatures.getUpVote()+1);
            newImageFeatures.setDownVote(imageFeatures.getDownVote());
        }else {
            newImageFeatures.setUpVote(imageFeatures.getUpVote());
            newImageFeatures.setDownVote(imageFeatures.getDownVote()+1);
        }
        myDatabaseRef.child("current").child(imageFeatures.getUuid()).setValue(newImageFeatures);

    }



    private void uploadProfile(){
        ProfileFeatures newProfile=profileFeatures;
        if(selected==0)return;
        if(newProfile!=null) {
            newProfile.setCoins(profileFeatures.getCoins() + 10);
            List<String> newUUid;
            newUUid = profileFeatures.getUuid();
            newUUid.add(imageFeatures.getUuid());
            newProfile.setUuid(newUUid);
        }
        else {
            newProfile=new ProfileFeatures();
            newProfile.setCoins(10);
            newProfile.setEmail(user.getEmail());
            List<String> newUUid=new ArrayList<>();
            newUUid.add(imageFeatures.getUuid());
            newProfile.setUuid(newUUid);
        }
        myDatabaseRef.child("users").child(user.getDisplayName()).setValue(newProfile);
        finish();
       // new SendVotes().execute();
    }


    private class SendVotes extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(Constants.NGROK_POST_SELECT);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                OutputStream os=urlConnection.getOutputStream();

                DataOutputStream wr = new DataOutputStream(os);

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("select" ,selected+"" );

                    wr.writeBytes(obj.toString());
                    Log.i("JSON Input", obj.toString());
                    wr.flush();
                    wr.close();
                    os.close();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                int responseCode = urlConnection.getResponseCode();
                Log.i("RsponseCode", "is "+responseCode);

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String server_response = readStream(urlConnection.getInputStream());
                    Log.i("Response",server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetVotes().execute();
        }
    }


    private class GetVotes extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(Constants.NGROK_GET_SELECT);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                OutputStream os=urlConnection.getOutputStream();

                DataOutputStream wr = new DataOutputStream(os);

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("post" ,user.getEmail() );

                    wr.writeBytes(obj.toString());
                    Log.i("JSON Input", obj.toString());
                    wr.flush();
                    wr.close();
                    os.close();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                int responseCode = urlConnection.getResponseCode();
                Log.i("RsponseCode", "is "+responseCode);

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String server_response = readStream(urlConnection.getInputStream());
                    Log.i("Response",server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
