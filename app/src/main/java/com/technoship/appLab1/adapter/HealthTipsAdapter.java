package com.technoship.appLab1.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.HealthTipItemBinding;
import com.technoship.appLab1.databinding.PreexamPackagesOffersAnnouncementItemBinding;
import com.technoship.appLab1.models.HealthTip;

import java.util.ArrayList;

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.MyViewHolder> {

    private final ArrayList<HealthTip> items;
    private HealthTipsAdapter.OnItemClickListener listener;

    public HealthTipsAdapter(ArrayList<HealthTip> items) {
        this.items = items;
    }

    public void addToTop(ArrayList<HealthTip> healthTips, RecyclerView recyclerView){
        for (int i=healthTips.size()-1; i>=0; i--){
            items.add(0, healthTips.get(i));
            notifyItemInserted(0);
        }
        recyclerView.scrollToPosition(0);
    }

    @NonNull
    @Override
    public HealthTipsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HealthTipsAdapter.MyViewHolder(HealthTipItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HealthTipsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.details.setText(items.get(position).getDetails());
        Glide.with(holder.itemView.getContext()).load(items.get(position).getImage()).placeholder(R.drawable.common_full_open_on_phone).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, details;
        private final ImageView image;
        public MyViewHolder(@NonNull HealthTipItemBinding itemView) {
            super(itemView.getRoot());

            title = itemView.title;
            details = itemView.details;
            image = itemView.img1;

            itemView.seeMoreButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onSeeMoreClicked(items.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(HealthTipsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onSeeMoreClicked(HealthTip HealthTip);
    }
}
