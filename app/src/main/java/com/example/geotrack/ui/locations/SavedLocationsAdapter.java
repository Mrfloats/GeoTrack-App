package com.example.geotrack.ui.locations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.geotrack.R;
import com.example.geotrack.data.entity.SavedLocation;
import java.util.ArrayList;
import java.util.List;

public class SavedLocationsAdapter extends RecyclerView.Adapter<SavedLocationsAdapter.LocationViewHolder> {
    private List<SavedLocation> locations = new ArrayList<>();
    private final OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(SavedLocation location);
    }

    public SavedLocationsAdapter(OnLocationClickListener listener) {
        this.listener = listener;
    }

    public void setLocations(List<SavedLocation> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        SavedLocation location = locations.get(position);
        holder.textName.setText(location.getName());
        holder.textCoordinates.setText(location.getLatitude() + ", " + location.getLongitude());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLocationClick(location);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textCoordinates;

        LocationViewHolder(View itemView) {
            super(itemView);
            // Updated to match IDs in item_location.xml
            textName = itemView.findViewById(R.id.tv_location_name);
            textCoordinates = itemView.findViewById(R.id.tv_lat_lng_sub);
        }
    }
}
