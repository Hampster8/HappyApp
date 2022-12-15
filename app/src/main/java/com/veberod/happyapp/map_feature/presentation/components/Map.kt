package com.veberod.happyapp.map_feature.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map() {
    val veberod = LatLng(55.6364, 13.5006)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(veberod, 15f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = veberod),
            title = "Veberöd",
            //snippet = ""
        )

    }

}