package com.veberod.happyapp.feature_smilies.presentation.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun getCurrentLocation(onSuccess: (Location) -> Unit, onFailure: (String) -> Unit) {
        if (!checkPermissions()) {
            onFailure("Location permission not granted")
            return
        }

        if (!isLocationEnabled()) {
            onFailure("Location providers not enabled")
            return
        }

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        onSuccess(location)
                    } else {
                        onFailure("No location available")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("LocationHelper", "Error getting location", exception)
                    onFailure("Error getting location")
                }
        } catch (securityException: SecurityException) {
            Log.e("LocationHelper", "Error getting location", securityException)
            onFailure("Location permission not granted")
        }
    }
}
