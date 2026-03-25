package com.example.geotrack.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.geotrack.data.AppDatabase;
import com.example.geotrack.data.dao.SavedLocationDao;
import com.example.geotrack.data.entity.SavedLocation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationRepositoryImpl {
    private final SavedLocationDao savedLocationDao;
    private final LiveData<List<SavedLocation>> allLocations;
    private final ExecutorService executorService;

    public LocationRepositoryImpl(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        savedLocationDao = db.savedLocationDao();
        allLocations = savedLocationDao.getAllLocations();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void saveLocation(SavedLocation location) {
        executorService.execute(() -> savedLocationDao.insertLocation(location));
    }

    public void deleteLocation(SavedLocation location) {
        executorService.execute(() -> savedLocationDao.deleteLocation(location));
    }

    public LiveData<List<SavedLocation>> getAllLocations() {
        return allLocations;
    }
}
