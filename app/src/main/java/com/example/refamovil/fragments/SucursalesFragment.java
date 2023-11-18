package com.example.refamovil.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.refamovil.R;
import com.example.refamovil.databinding.ActivityFragmentNuestrasSucursalesBinding;


import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.maps.model.DirectionsRoute;

import java.util.List;

public class SucursalesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityFragmentNuestrasSucursalesBinding binding;

    private int minimumDistance = 30;
    private final int PERMISSION_LOCATION = 999;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng origenLatLng;
    private LatLng destinoLatLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ActivityFragmentNuestrasSucursalesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(minimumDistance);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.e("Sesion11",
                        locationResult.getLastLocation().getLatitude() + "," +
                                locationResult.getLastLocation().getLongitude());
            }
        };

        return view;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (origenLatLng != null) {
            // Si ya hay un origen y un destino seleccionados, borra la polilínea existente
            mMap.clear();
            origenLatLng = null;
        }

        // Determina si el usuario está seleccionando el origen o el destino
        if (origenLatLng == null) {
            origenLatLng = latLng;
            // Marca el lugar de origen en el mapa, por ejemplo, con un marcador
            mMap.addMarker(new MarkerOptions().position(origenLatLng).title("Mi Casa"));
            obtenerYDibujarRuta();
        } else {
            Toast.makeText(getContext(), "Error en el mapa", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (permissions[0].equalsIgnoreCase(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSION_LOCATION);
        }

        LatLng gdl = new LatLng(20.702885910910105, -103.38889174914175);
        destinoLatLng = gdl;
        mMap.addMarker(new MarkerOptions().position(gdl).title("RefaMovil")
                .snippet("El mejor lugar en refacciones de todo Mexico"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gdl, 15));
        mMap.setOnMapClickListener(this);
    }

    private void startLocationUpdates() {
        try {
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            // Handle exception
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onStart() {
        super.onStart();
        startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void obtenerYDibujarRuta() {
        if (origenLatLng != null && destinoLatLng != null) {
            // Configura el contexto de la API de Google Maps
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyAVdDlU5_oeaxOgfXEEQ_XaQa0wWRppyP0")
                    .build();

            try {
                // Realiza la solicitud de direcciones
                DirectionsResult result = DirectionsApi.newRequest(context)
                        .origin(new com.google.maps.model.LatLng(origenLatLng.latitude, origenLatLng.longitude))
                        .destination(new com.google.maps.model.LatLng(destinoLatLng.latitude, destinoLatLng.longitude))
                        .mode(TravelMode.DRIVING) // Puedes cambiar el modo según tus necesidades (por ejemplo, WALKING para caminar)
                        .await();

                // Obtiene la ruta preferida (por ejemplo, la ruta principal)
                DirectionsRoute route = result.routes[0];

                // Dibuja la polilínea en el mapa
                List<com.google.maps.model.LatLng> path = route.overviewPolyline.decodePath();
                PolylineOptions polylineOptions = new PolylineOptions();
                for (com.google.maps.model.LatLng latLng : path) {
                    polylineOptions.add(new LatLng(latLng.lat, latLng.lng));
                }
                mMap.addPolyline(polylineOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
