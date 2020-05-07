package com.company.ab.activity.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ab.R;
import com.company.ab.adapter.RecyclerViewCurrentAdapter;
import com.company.ab.adapter.RecyclerviewResultAdapter;
import com.company.ab.database.ImageFeatures;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment  {


    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private GalleryViewModel galleryViewModel;

    private RecyclerView recyclerViewCurrentAdapter,recyclerViewResultAdapter;
    private RecyclerView.Adapter adapter;

    private List<ImageFeatures> currentList = new ArrayList<>();
    private List<ImageFeatures> resultList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerViewCurrentAdapter=root.findViewById(R.id.recyclerview_current);
        recyclerViewCurrentAdapter.setHasFixedSize(true);
        recyclerViewCurrentAdapter.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewResultAdapter=root.findViewById(R.id.recyclerview_result);
        recyclerViewResultAdapter.setHasFixedSize(true);
        recyclerViewResultAdapter.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Survey Name...");





        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.child("current").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ImageFeatures imageUploadInfo = postSnapshot.getValue(ImageFeatures.class);
                    currentList.add(imageUploadInfo);
                }
                adapter = new RecyclerViewCurrentAdapter(getContext(), currentList);
                recyclerViewCurrentAdapter.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

        databaseReference.child("result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                resultList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ImageFeatures imageUploadInfo = postSnapshot.getValue(ImageFeatures.class);
                    resultList.add(imageUploadInfo);
                }
                adapter=new RecyclerviewResultAdapter(getContext(),resultList);
                recyclerViewResultAdapter.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


        return root;
    }

}
