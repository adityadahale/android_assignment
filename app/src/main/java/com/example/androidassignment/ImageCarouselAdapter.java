package com.example.androidassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ImageCarouselAdapter extends RecyclerView.Adapter<ImageCarouselAdapter.ViewHolder> {
    private List<Integer> imageUrls;

    public ImageCarouselAdapter(List<Integer> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageUrl = imageUrls.get(position);

        // Load image using Glide with placeholder and error handling
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                        .skipMemoryCache(false)) // Skip memory cache
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageUrls != null ? imageUrls.size() : 0; // Safely handle null list
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
