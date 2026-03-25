package com.example.geotrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class CustomLocation {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double latitude;
    public double longitude;
    public float radius;

    public CustomLocation(String name, double latitude, double longitude, float radius) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}
