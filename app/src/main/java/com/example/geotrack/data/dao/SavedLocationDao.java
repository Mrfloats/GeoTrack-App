package com.example.geotrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.geotrack.data.entity.SavedLocation;
import java.util.List;

@Dao
public interface SavedLocationDao {
    @Insert
    void insertLocation(SavedLocation location);

    @Delete
    void deleteLocation(SavedLocation location);

    @Query("SELECT * FROM saved_locations ORDER BY id DESC")
    LiveData<List<SavedLocation>> getAllLocations();
}
