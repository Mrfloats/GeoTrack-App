package com.example.geotrack.ui.locations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.geotrack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SavedLocationsFragment extends Fragment {
    private SavedLocationsViewModel viewModel;
    private SavedLocationsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView setup
        RecyclerView recyclerView = view.findViewById(R.id.rv_locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SavedLocationsAdapter(location -> {
            // Launch Google Maps navigation intent
            Uri gmmIntentUri = Uri
                    .parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback if Google Maps app is not installed
                startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
            }
        });
        recyclerView.setAdapter(adapter);

        // Floating Action Button setup
        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        if (fabAdd != null) {
            fabAdd.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AddLocationActivity.class);
                startActivity(intent);
            });
        }

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(SavedLocationsViewModel.class);
        viewModel.getAllLocations().observe(getViewLifecycleOwner(), locations -> {
            if (locations != null) {
                adapter.setLocations(locations);
            }
        });
    }
}
