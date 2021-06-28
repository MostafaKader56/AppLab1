package com.technoship.appLab1.adapter.packagesAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.PreexamPackagesOffersAnnouncementItemBinding;
import com.technoship.appLab1.models.Package;

public class PackagesAdapter extends FirestoreRecyclerAdapter<Package, PackagesAdapter.MyViewHolder> {

    private OnItemClickListener listener;

    public PackagesAdapter(@NonNull FirestoreRecyclerOptions<Package> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        // do your thing
        listener.onEmpty(getItemCount());
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Package model) {
        holder.name.setText(model.getTitle());
        holder.address.setText(model.getDetails());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackagesAdapter.MyViewHolder(PreexamPackagesOffersAnnouncementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, address;
        public MyViewHolder(@NonNull PreexamPackagesOffersAnnouncementItemBinding itemView) {
            super(itemView.getRoot());

            name = itemView.title;
            address = itemView.details;
            itemView.img1.setImageResource(R.drawable.packing);


            itemView.seeMoreButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onClicked(getItem(position));
                }
            });
        }
    }

    public void setOnItemClickListener(PackagesAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onEmpty(int itemsNum);
        void onClicked(Package _package);
    }
}
