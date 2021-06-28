package com.technoship.appLab1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.PreexamPackagesOffersAnnouncementItemBinding;
import com.technoship.appLab1.models.ExaminationPrecaution;

import java.util.ArrayList;

public class ExaminationPrecautionsAdapter extends RecyclerView.Adapter<ExaminationPrecautionsAdapter.MyViewHolder> {

    private final ArrayList<ExaminationPrecaution> items;
    private OnItemClickListener listener;

    public ExaminationPrecautionsAdapter(ArrayList<ExaminationPrecaution> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ExaminationPrecautionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(PreexamPackagesOffersAnnouncementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExaminationPrecautionsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.details.setText(items.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView details;
        public MyViewHolder(@NonNull PreexamPackagesOffersAnnouncementItemBinding itemView) {
            super(itemView.getRoot());
            title = itemView.title;
            details = itemView.details;
            itemView.img1.setImageResource(R.drawable.syringeinlist);

            itemView.seeMoreButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onSeeMoreClicked(items.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onSeeMoreClicked(ExaminationPrecaution examinationPrecaution);
    }
}
