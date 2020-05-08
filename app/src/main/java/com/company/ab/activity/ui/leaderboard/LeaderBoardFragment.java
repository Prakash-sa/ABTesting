package com.company.ab.activity.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.company.ab.R;
import com.company.ab.adapter.CustomAdapter;
import com.company.ab.database.ListData;
import com.company.ab.database.ProfileFeatures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LeaderBoardFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("");
    private FirebaseUser user;

    private LeaderBoardViewModel leaderBoardViewModel;

    private ListView listView;
    private ArrayList<ListData>listData=new ArrayList<>();
    private List<Pair<Integer,String>>participants=new ArrayList<Pair<Integer,String>>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderBoardViewModel =
                ViewModelProviders.of(this).get(LeaderBoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        listView=root.findViewById(R.id.leaderboard_listview_id);

        user = FirebaseAuth.getInstance().getCurrentUser();

        getFromFirebase();

        return root;
    }

    private void getFromFirebase() {
        myRef.child("users").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                participants.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    ProfileFeatures tmp=dataSnapshot1.getValue(ProfileFeatures.class);
                    participants.add(new Pair<Integer, String>(tmp.getCoins(),tmp.getEmail()));
                }
                Collections.sort(participants,Collections.<Pair<Integer, String>>reverseOrder());
                addListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addListView(){
        listData.clear();
        for(int i=0;i<participants.size();i++){
            listData.add(new ListData(participants.get(i).second,participants.get(i).first,i+1));
        }
        if(listData.size()==0)return;
        CustomAdapter customAdapter = new CustomAdapter(getContext(), listData);
        listView.setAdapter(customAdapter);
    }


}
