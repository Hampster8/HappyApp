package com.veberod.happyapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.R

@Preview
@Composable
fun Smilies() {
    val modifier = Modifier
        .padding(8.dp)
        .size(100.dp)
        .clickable { /*TODO here is to score the mood and send to DB*/ }
    Column (verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Card(elevation = 8.dp) {
            Image(
                painterResource(R.drawable.smilie1happyapp),
                contentDescription = "Smiley 1",
                modifier)
        }
        Card(elevation = 8.dp){
            Image(
                painterResource(R.drawable.smilie2happyapp),
                contentDescription = "Smiley 2",
                modifier)
        }
        Card(elevation = 8.dp){
            Image(
                painterResource(R.drawable.smilie3happyapp),
                contentDescription = "Smiley 3",
                modifier)
        }
        Card(elevation = 8.dp){
            Image(
                painterResource(R.drawable.smilie4happyapp),
                contentDescription = "Smiley 4",
                modifier)
        }
        Card(elevation = 8.dp){
            Image(
                painterResource(R.drawable.smilie5happyapp),
                contentDescription = "Smiley 5",
                modifier)
        }

        }

    }



