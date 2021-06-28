package com.technoship.appLab1.adapter.branchesAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.technoship.appLab1.databinding.BranchItemBinding;
import com.technoship.appLab1.models.Branch;

public class BranchesAdapterAr extends FirestoreRecyclerAdapter<Branch, BranchesAdapterAr.MyViewHolder> {

    private OnItemClickListener listener;

    public BranchesAdapterAr(@NonNull FirestoreRecyclerOptions<Branch> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        // do your thing
        listener.onEmpty(getItemCount());
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Branch model) {
        holder.name.setText(model.getNameAr());
        holder.address.setText(model.getAddressAr());
        holder.phone.setText(model.getPhone());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BranchesAdapterAr.MyViewHolder(BranchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, address, phone;
        public MyViewHolder(@NonNull BranchItemBinding itemView) {
            super(itemView.getRoot());

            name = itemView.name;
            address = itemView.address;
            phone = itemView.phone;

            itemView.mapButton.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onMapButtonClicked(getItem(position), name.getText().toString());
                }
            });
        }
    }

    public void setOnItemClickListener(BranchesAdapterAr.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onEmpty(int itemsNum);
        void onMapButtonClicked(Branch branch, String mapMarketTitle);
    }
}
