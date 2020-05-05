package com.company.ab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.ab.R;
import com.company.ab.database.ImageFeatures;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<ImageFeatures> MainStoreImageHelperList;

    public RecyclerViewAdapter(Context context, List<ImageFeatures> TempList) {

        this.MainStoreImageHelperList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageFeatures UploadInfo = MainStoreImageHelperList.get(position);

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

        public ViewHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.image1_id);
            imageView2 = (ImageView) itemView.findViewById(R.id.image2_id);
            imageTextView1=itemView.findViewById(R.id.text1_id);
            imageTextView2=itemView.findViewById(R.id.text2_id);
        }
    }
}