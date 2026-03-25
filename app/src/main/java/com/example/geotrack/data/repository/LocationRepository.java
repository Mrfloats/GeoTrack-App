package com.example.geotrack.data.repository;

import androidx.lifecycle.LiveData;
import com.example.geotrack.data.entity.CustomLocation;
import java.util.List;

public interface LocationRepository {
    void saveLocation(CustomLocation location);
    void deleteLocation(CustomLocation location);
    LiveData<List<CustomLocation>> getAllLocations();
}
