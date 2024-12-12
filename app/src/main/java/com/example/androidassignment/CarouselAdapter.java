package com.example.androidassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> implements Filterable {

    private List<ListItem> carouselItems;
    private List<ListItem> originalList; // Store the original list

    public CarouselAdapter(List<ListItem> carouselItems) {
        this.carouselItems = new ArrayList<>(carouselItems); // Copy to avoid modification
        this.originalList = new ArrayList<>(carouselItems); // Save original list
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        ListItem item = carouselItems.get(position);

        // Load image using Glide or any other image loading library
        Glide.with(holder.imageView.getContext())
                .load(item.getImageUrl())
                .into(holder.imageView);

        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ListItem> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // If the search query is empty, return the original list
                    filteredList.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ListItem item : originalList) {
                        // Customize this condition as per your filtering requirements
                        if (item.getTitle().toLowerCase().contains(filterPattern) ||
                                item.getSubtitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    carouselItems.clear();
                    carouselItems.addAll((List<ListItem>) results.values);
                    notifyDataSetChanged(); // Notify adapter of dataset change
                }
            }
        };
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView subtitle;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
            subtitle = itemView.findViewById(R.id.item_subtitle);
        }
    }
}
