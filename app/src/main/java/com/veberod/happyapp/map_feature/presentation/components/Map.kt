package com.veberod.happyapp.map_feature.presentation.components


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider

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
        val heatMapPoints: List<LatLng> = listOf(
            LatLng(55.6350, 13.4958),
            LatLng(55.6355, 13.4955),
            LatLng(55.6360, 13.4968),
            LatLng(55.6365, 13.4965),
            LatLng(55.6365, 13.4965),
            LatLng(55.6365, 13.4965),
            LatLng(55.6365, 13.4965),
            LatLng(55.6365, 13.4965),
            LatLng(55.6365, 13.4965))
        // Create a HeatmapTileProvider object using the specified list of LatLng points
        val heatmapTileProvider = HeatmapTileProvider.Builder()
            .data(heatMapPoints)
            .opacity(0.5)
            .build()
        // Add a TileOverlay to the map using the HeatmapTileProvider object as the tile provider
        TileOverlay(tileProvider = heatmapTileProvider)
    }
}