package com.example.geotrack.ui.locations;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.geotrack.R;
import com.example.geotrack.data.entity.SavedLocation;
import com.example.geotrack.services.LocationTracker;
import com.example.geotrack.ui.map.MapViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddLocationActivity extends AppCompatActivity {

    private TextInputEditText etName;
    private TextView tvLatLng;
    private MaterialButton btnSave;
    private MapViewModel viewModel;
    private LocationTracker locationTracker;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        etName = findViewById(R.id.et_name);
        tvLatLng = findViewById(R.id.tv_lat_lng);
        btnSave = findViewById(R.id.btn_save);

        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        locationTracker = new LocationTracker(this);

        fetchCurrentLocation();

        btnSave.setOnClickListener(v -> saveLocation());
    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        locationTracker.getLastLocation(location -> {
            if (location != null) {
                currentLocation = location;
                tvLatLng.setText(String.format("Lat: %.6f, Lng: %.6f", location.getLatitude(), location.getLongitude()));
            } else {
                tvLatLng.setText("Unable to fetch location. Ensure GPS is on.");
            }
        });
    }

    private void saveLocation() {
        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            etName.setError("Enter a name");
            return;
        }

        if (currentLocation == null) {
            Toast.makeText(this, "Wait for location to be detected", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.saveLocationWithGeofence(name, currentLocation.getLatitude(), currentLocation.getLongitude(), 200f);
        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchCurrentLocation();
        }
    }
}
