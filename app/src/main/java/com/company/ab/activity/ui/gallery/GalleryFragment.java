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

        ImageFeatures imageFeatures=new ImageFeatures("https://image.shutterstock.com/image-photo/large-beautiful-drops-transparent-rain-600w-668593321.jpg",
                "https://image.shutterstock.com/image-photo/beautiful-landscape-mountain-layer-morning-600w-753385105.jpg",
                "adf",
                10, "cook","cook2");
        currentList.add(imageFeatures);
        adapter = new RecyclerViewCurrentAdapter(getContext(), currentList);
        recyclerViewCurrentAdapter.setAdapter(adapter);

        resultList.add(imageFeatures);
        adapter=new RecyclerviewResultAdapter(getContext(),resultList);
        recyclerViewResultAdapter.setAdapter(adapter);

        /*
        // Showing progress dialog.
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("admin/");
        databaseReference=databaseReference.child("watchman");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ImageFeatures imageUploadInfo = postSnapshot.getValue(ImageFeatures.class);
                    list.add(imageUploadInfo);
                }
                adapter = new RecyclerViewCurrentAdapter(getContext(), list);
                recyclerViewCurrentAdapter.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

         */
        return root;
    }

}
