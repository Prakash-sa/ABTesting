package com.company.ab.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.ab.R;
import com.company.ab.activity.ImageViewActivity;
import com.company.ab.database.ImageFeatures;

import java.util.List;

public class RecyclerviewResultAdapter extends RecyclerView.Adapter<RecyclerviewResultAdapter.ViewHolder> {
    Context context;
    List<ImageFeatures> MainStoreImageHelperList;

    public RecyclerviewResultAdapter(Context context, List<ImageFeatures> TempList) {

        this.MainStoreImageHelperList = TempList;

        this.context = context;
    }

    @Override
    public RecyclerviewResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        RecyclerviewResultAdapter.ViewHolder viewHolder = new RecyclerviewResultAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerviewResultAdapter.ViewHolder holder, int position) {
        final ImageFeatures UploadInfo = MainStoreImageHelperList.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ImageViewActivity.class);
                intent.putExtra("imageurl1",UploadInfo.getImageurl1());
                intent.putExtra("imageurl2",UploadInfo.getImageurl2());

                Pair<View, String> p1 = Pair.create((View)holder.imageTextView1, "imageView1");
                Pair<View, String> p2 = Pair.create((View)holder.imageTextView2, "imageView2");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( (Activity) context, p1, p2);
                context.startActivity(intent,options.toBundle());
            }
        });
        holder.imageTextView1.setText(UploadInfo.getImagetext1());
        holder.imageTextView2.setText(UploadInfo.getImagetext2());
        Glide.with(context).load(UploadInfo.getImageurl1()).into(holder.imageView1);
        Glide.with(context).load(UploadInfo.getImageurl2()).into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        return MainStoreImageHelperList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView1,imageView2;
        public TextView imageTextView1,imageTextView2;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.lineraLayout_id);
            imageView1 = (ImageView) itemView.findViewById(R.id.image1_id);
            imageView2 = (ImageView) itemView.findViewById(R.id.image2_id);
            imageTextView1=itemView.findViewById(R.id.text1_id);
            imageTextView2=itemView.findViewById(R.id.text2_id);
        }
    }
}