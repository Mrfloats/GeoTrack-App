package com.example.geotrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.geotrack.data.entity.CustomLocation;
import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    long insertLocation(CustomLocation location);

    @Delete
    void deleteLocation(CustomLocation location);

    @Query("SELECT * FROM locations")
    LiveData<List<CustomLocation>> getAllLocations();

    @Query("SELECT * FROM locations WHERE id = :id")
    CustomLocation getLocationById(int id);
}
