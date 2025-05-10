package com.example.curetrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<Hospital> hospitals;
    private Context context;

    public HospitalAdapter(Context context, List<Hospital> hospitals) {
        this.context = context;
        this.hospitals = hospitals;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, beds, phone;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.hospitalImage);
            name = view.findViewById(R.id.hospitalName);
            beds = view.findViewById(R.id.availableBeds);
            phone = view.findViewById(R.id.phone);

            view.setOnClickListener(v -> {
                Hospital selected = hospitals.get(getAdapterPosition());
                Intent intent = new Intent(context, HospitalDetailsActivity.class);
                intent.putExtra("hospitalName", selected.getName());
                intent.putExtra("phone", selected.getPhone());
                intent.putExtra("imageUrl", selected.getImageUrl());
                intent.putExtra("totalBeds", selected.getBeds());
                context.startActivity(intent);
            });

        }
    }

    @Override
    public HospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HospitalAdapter.ViewHolder holder, int position) {
        Hospital h = hospitals.get(position);
        holder.name.setText(h.getName() != null ? h.getName() : "No Name");
        holder.beds.setText("Beds: " + h.getBeds());
        holder.phone.setText("Ph no. " + (h.getPhone() != null ? h.getPhone() : "N/A"));

        // Uncomment and use if image loading is needed
        // Glide.with(context)
        //     .load(h.getImageUrl())
        //     .placeholder(R.drawable.placeholder_image)
        //     .into(holder.image);
    }


    @Override
    public int getItemCount() {
        return hospitals.size();
    }
}
