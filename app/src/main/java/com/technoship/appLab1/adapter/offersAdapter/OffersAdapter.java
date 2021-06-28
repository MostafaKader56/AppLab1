package com.technoship.appLab1.adapter.offersAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.PreexamPackagesOffersAnnouncementItemBinding;
import com.technoship.appLab1.models.Offer;

public class OffersAdapter extends FirestoreRecyclerAdapter<Offer, OffersAdapter.MyViewHolder> {

    private OffersAdapter.OnItemClickListener listener;

    public OffersAdapter(@NonNull FirestoreRecyclerOptions<Offer> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

        listener.onEmpty(getItemCount());
    }

    @Override
    protected void onBindViewHolder(@NonNull OffersAdapter.MyViewHolder holder, int position, @NonNull Offer model) {
        holder.name.setText(model.getTitle());
        holder.address.setText(model.getDetails());
    }

    @NonNull
    @Override
    public OffersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OffersAdapter.MyViewHolder(PreexamPackagesOffersAnnouncementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, address;
        public MyViewHolder(@NonNull PreexamPackagesOffersAnnouncementItemBinding itemView) {
            super(itemView.getRoot());

            name = itemView.title;
            address = itemView.details;
            itemView.img1.setImageResource(R.drawable.offer);


            itemView.seeMoreButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onClicked(getItem(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OffersAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onEmpty(int itemsNum);
        void onClicked(Offer _Offer);
    }
}
