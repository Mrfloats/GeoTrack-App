package com.example.geotrack.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

/**
 * Helper class for GPS tracking using FusedLocationProviderClient.
 * This handles continuous location updates and provides them to the UI via a listener.
 */
public class LocationTracker {

    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationRequest locationRequest;
    private final LocationCallback locationCallback;
    private OnLocationUpdateListener listener;
    private final Context context;

    /**
     * Interface to return location results to the UI.
     */
    public interface OnLocationUpdateListener {
        void onLocationUpdate(Location location);
    }

    public LocationTracker(Context context) {
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // 1. Create a LocationRequest with high accuracy
        this.locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000) // Update every 5 seconds
                .setMinUpdateIntervalMillis(2000) // Minimum 2 seconds between updates
                .build();

        // 2. Define the callback that receives location results
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    if (location != null && listener != null) {
                        listener.onLocationUpdate(location);
                    }
                }
            }
        };
    }

    /**
     * Set the listener to receive location updates.
     */
    public void setOnLocationUpdateListener(OnLocationUpdateListener listener) {
        this.listener = listener;
    }

    /**
     * Start requesting location updates.
     * Ensure permissions are checked before calling this.
     */
    public void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        }
    }

    /**
     * Stop requesting location updates to save battery.
     */
    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    /**
     * Simple method to get the last known location once.
     */
    public void getLastLocation(OnLocationUpdateListener callback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    callback.onLocationUpdate(location);
                }
            });
        }
    }
}
