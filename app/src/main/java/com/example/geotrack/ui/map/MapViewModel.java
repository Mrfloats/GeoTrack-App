package com.example.geotrack.ui.map;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.geotrack.data.entity.SavedLocation;
import com.example.geotrack.data.repository.LocationRepositoryImpl;
import com.example.geotrack.services.GeofenceManager;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private final LocationRepositoryImpl repository;
    private final GeofenceManager geofenceManager;
    private final MutableLiveData<LatLng> currentLocation = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepositoryImpl(application);
        geofenceManager = new GeofenceManager(application);
    }

    public LiveData<List<SavedLocation>> getSavedLocations() {
        return repository.getAllLocations();
    }

    public void saveLocationWithGeofence(String name, double lat, double lng, float radius) {
        SavedLocation location = new SavedLocation(name, lat, lng);
        repository.saveLocation(location);
        // We use name + lat as a simple unique ID for the geofence request
        geofenceManager.addGeofence(name + lat, lat, lng, radius);
    }

    public LiveData<LatLng> getCurrentLocation() {
        return currentLocation;
    }

    public void updateCurrentLocation(LatLng latLng) {
        currentLocation.postValue(latLng);
    }
}
