package com.example.geotrack.ui.locations;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.geotrack.data.entity.SavedLocation;
import com.example.geotrack.data.repository.LocationRepositoryImpl;
import java.util.List;

public class SavedLocationsViewModel extends AndroidViewModel {
    private final LocationRepositoryImpl repository;

    public SavedLocationsViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepositoryImpl(application);
    }

    public LiveData<List<SavedLocation>> getAllLocations() {
        return repository.getAllLocations();
    }

    public void deleteLocation(SavedLocation location) {
        repository.deleteLocation(location);
    }
}
