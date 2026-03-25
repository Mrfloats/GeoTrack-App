package com.example.geotrack.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.geotrack.R;
import com.example.geotrack.services.LocationTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";
    private MapViewModel viewModel;
    private GoogleMap mMap;
    private LocationTracker locationTracker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        locationTracker = new LocationTracker(requireContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "Map is ready");

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            locationTracker.getLastLocation(location -> {
                if (location != null) {
                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    viewModel.updateCurrentLocation(current);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15f));
                }
            });
        }

        mMap.setOnMapLongClickListener(latLng -> {
            String name = "Location " + System.currentTimeMillis();
            Log.d(TAG, "Saving location: " + name + " at " + latLng.toString());
            viewModel.saveLocationWithGeofence(name, latLng.latitude, latLng.longitude, 200f);
            Toast.makeText(requireContext(), "Saving Location...", Toast.LENGTH_SHORT).show();
        });

        viewModel.getSavedLocations().observe(getViewLifecycleOwner(), locations -> {
            if (locations != null) {
                Log.d(TAG, "Observed " + locations.size() + " locations from database");
                mMap.clear();
                for (com.example.geotrack.data.entity.SavedLocation loc : locations) {
                    LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(pos).title(loc.getName()));
                }
            }
        });
    }
}
