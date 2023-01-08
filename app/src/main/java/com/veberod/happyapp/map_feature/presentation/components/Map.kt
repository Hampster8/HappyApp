package com.veberod.happyapp.map_feature.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.veberod.happyapp.database.domain.model.LatLngConverter
import com.veberod.happyapp.database.domain.repository.MoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Map(moodRepository: MoodRepository) {
    val veberod = LatLng(55.6364, 13.5006)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(veberod, 15f)
    }
    val heatMapPoints: MutableList<LatLng> = mutableListOf()
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = veberod),
            title = "Veberöd"

        )
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            withContext(Dispatchers.IO) {
                val moods = moodRepository.getAllMoods()
                val latLngConverter = LatLngConverter()
                for (mood in moods) {
                    val latLng = latLngConverter.toLatLng(mood.geolocation)
                    // Add the LatLng point to the heatMapPoints list
                    heatMapPoints.add(latLng)
                }
            }
        }

        val heatmapTileProvider = HeatmapTileProvider.Builder()
            .data(heatMapPoints)
            .opacity(0.5)
            .build()
        // Create a HeatmapTileProvider object using the heatMapPoints list as the data

        // Add a TileOverlay to the map using the HeatmapTileProvider object as the tile provider
        TileOverlay(tileProvider = heatmapTileProvider)

    }

}




