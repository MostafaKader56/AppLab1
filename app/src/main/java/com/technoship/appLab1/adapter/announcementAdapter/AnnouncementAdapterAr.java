package com.technoship.appLab1.adapter.announcementAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.PreexamPackagesOffersAnnouncementItemBinding;
import com.technoship.appLab1.models.Announcement;

public class AnnouncementAdapterAr extends FirestoreRecyclerAdapter<Announcement, AnnouncementAdapterAr.MyViewHolder> {

    private AnnouncementAdapterAr.OnItemClickListener listener;

    public AnnouncementAdapterAr(@NonNull FirestoreRecyclerOptions<Announcement> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        // do your thing
        listener.onEmpty(getItemCount());
    }

    @Override
    protected void onBindViewHolder(@NonNull AnnouncementAdapterAr.MyViewHolder holder, int position, @NonNull Announcement model) {
        holder.name.setText(model.getTitleAr());
        holder.address.setText(model.getDetailsAr());
    }

    @NonNull
    @Override
    public AnnouncementAdapterAr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnnouncementAdapterAr.MyViewHolder(PreexamPackagesOffersAnnouncementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, address;
        public MyViewHolder(@NonNull PreexamPackagesOffersAnnouncementItemBinding itemView) {
            super(itemView.getRoot());

            name = itemView.title;
            address = itemView.details;
            itemView.img1.setImageResource(R.drawable.announcement);


            itemView.seeMoreButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onClicked(getItem(position));
                }
            });
        }
    }

    public void setOnItemClickListener(AnnouncementAdapterAr.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onEmpty(int itemsNum);
        void onClicked(Announcement _Announcement);
    }
}

