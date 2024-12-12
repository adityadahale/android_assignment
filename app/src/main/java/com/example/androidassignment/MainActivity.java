package com.example.androidassignment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<Integer> imageList; // List of images
    private Handler handler;
    private Runnable imageSliderRunnable;
    private LinearLayout dotsLayout;  // Layout for custom indicators
    private ImageView[] dots;  // Array of indicator dots
    private List<CarouselItem> carouselItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Turn off Dark Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SearchView searchView = findViewById(R.id.searchView);

        // Remove the underline
        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }// Or use `searchPlate.setBackground(null);

        // Initialize image list with dummy data
        imageList = Arrays.asList(
                R.drawable.taj_mahal,
                R.drawable.udaipur,
                R.drawable.usa,
                R.drawable.paris,
                R.drawable.japan
        );


        FloatingActionButton fabButton = findViewById(R.id.fab);

        // Initialize carousel data
        carouselItems = Arrays.asList(
                new CarouselItem("https://imgur.com/2UkCwdk.jpg", "Title 1", "Subtitle 1"),
                new CarouselItem("https://example.com/image2.jpg", "Title 2", "Subtitle 2"),
                new CarouselItem("https://example.com/image3.jpg", "Title 3", "Subtitle 3")
        );


        List<ListItem> carouselItems1 = Arrays.asList(
                new ListItem(R.drawable.taj_mahal, "Taj Mahal, Agra", "Symbol of love, UNESCO World Heritage Site"),
                new ListItem(R.drawable.jaipur, "Jaipur, Rajasthan", "Pink City, Amber Fort, Hawa Mahal"),
                new ListItem(R.drawable.kerala, "Kerala Backwaters", "Serene houseboat cruises through scenic backwaters"),
                new ListItem(R.drawable.varanasi, "Varanasi, Uttar Pradesh", "Spiritual capital of India, Ganges ghats"),
                new ListItem(R.drawable.goa, "Goa", "Beaches, vibrant nightlife, Portuguese architecture"),
                new ListItem(R.drawable.leh, "Leh-Ladakh, Jammu & Kashmir", "Breathtaking landscapes, monasteries, trekking"),
                new ListItem(R.drawable.udaipur, "Udaipur, Rajasthan", "City of Lakes, beautiful palaces, and temples"),
                new ListItem(R.drawable.mysore, "Mysore, Karnataka", "Mysore Palace, Dussehra Festival, Chamundi Hill"),
                new ListItem(R.drawable.andaman, "Andaman and Nicobar Islands", "Pristine beaches, coral reefs, water sports"),
                new ListItem(R.drawable.paris, "Paris, France", "City of Lights, Eiffel Tower, Louvre Museum"),
                new ListItem(R.drawable.jaipur, "Kyoto, Japan", "Beautiful temples, cherry blossoms, and traditional tea houses"),
                new ListItem(R.drawable.usa, "New York City, USA", "Statue of Liberty, Times Square, Central Park"),
                new ListItem(R.drawable.maldives, "The Maldives", "Private beaches, crystal clear water, overwater bungalows"),
                new ListItem(R.drawable.italy, "Rome, Italy", "Colosseum, Roman Forum, Vatican City"),
                new ListItem(R.drawable.greece, "Santorini, Greece", "White-washed buildings, stunning sunsets, crystal-clear waters"),
                new ListItem(R.drawable.dubai, "Dubai, UAE", "Burj Khalifa, Palm Jumeirah, luxury shopping"),
                new ListItem(R.drawable.peru, "Machu Picchu, Peru", "Ancient Incan city high in the Andes mountains"),
                new ListItem(R.drawable.australia, "Sydney, Australia", "Sydney Opera House, Bondi Beach, Harbour Bridge")
        );

        // Set up the ViewPager2 for image carousel
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageCarouselAdapter adapter = new ImageCarouselAdapter(imageList);
        viewPager.setAdapter(adapter);


        // Set up RecyclerView for the carousel items list
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Use CarouselAdapter for RecyclerView
        CarouselAdapter carouselAdapter = new CarouselAdapter(carouselItems1);
        recyclerView.setAdapter(carouselAdapter);
        // Set up the custom dots layout
        dotsLayout = findViewById(R.id.dotsIndicator);

        // Create dots based on the number of images
        dots = new ImageView[imageList.size()];
        for (int i = 0; i < imageList.size(); i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.circle_indicator_inactive);  // Set inactive dot initially
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);  // Add some spacing between dots
            dotsLayout.addView(dots[i], params);
        }

        // Handle page change in ViewPager2
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position);
            }
        });

        // Setup automatic sliding of images every 3 seconds
        handler = new Handler(Looper.getMainLooper());
        imageSliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % imageList.size(); // Loop back to the first image
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // Repeat every 3 seconds
            }
        };

        // Start the automatic sliding
        handler.postDelayed(imageSliderRunnable, 3000); // Start after 3 seconds

        fabButton.setOnClickListener(view -> showBottomSheetDialog(carouselItems1));

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                carouselAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the automatic sliding when the activity is paused
        handler.removeCallbacks(imageSliderRunnable);
    }

    // Update the dots based on the current selected image
    private void updateDots(int position) {
        for (int i = 0; i < imageList.size(); i++) {
            if (i == position) {
                dots[i].setImageResource(R.drawable.circle_indicator);  // Active dot
            } else {
                dots[i].setImageResource(R.drawable.circle_indicator_inactive);  // Inactive dot
            }
        }
    }

    private void showBottomSheetDialog(List<ListItem> items) {
        // Inflate the bottom sheet layout
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_statistics, null);

        // Initialize the bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        // Find and set TextViews
        TextView itemCountText = view.findViewById(R.id.item_count);
        TextView characterStatsText = view.findViewById(R.id.character_stats);

        // Calculate total items
        int totalItems = items.size();
        itemCountText.setText("Total Items: " + totalItems);

        // Calculate character occurrences
        StringBuilder charStats = new StringBuilder("Top Characters:\n");
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (ListItem item : items) {
            String title = item.getTitle().toLowerCase(); // Convert to lowercase
            for (char c : title.toCharArray()) {
                if (Character.isLetter(c)) { // Consider only letters
                    charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
                }
            }
        }

        // Sort characters by occurrence
        List<Map.Entry<Character, Integer>> sortedChars = new ArrayList<>(charCountMap.entrySet());
        sortedChars.sort((e1, e2) -> e2.getValue() - e1.getValue());

        // Add top 3 characters to the stats
        for (int i = 0; i < Math.min(3, sortedChars.size()); i++) {
            Map.Entry<Character, Integer> entry = sortedChars.get(i);
            charStats.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }

        characterStatsText.setText(charStats.toString());

        // Show the dialog
        bottomSheetDialog.show();
    }

}
