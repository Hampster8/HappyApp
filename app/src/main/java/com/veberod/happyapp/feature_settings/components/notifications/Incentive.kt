package com.veberod.happyapp.feature_settings.components.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.*

const val targetDaysInARow = 7


@Composable
fun TrackDays() {
    val showCoupon = remember { mutableStateOf(false) }
    //If there is date in database get it
    //Get days in a row from database
    val daysInARow = remember { mutableStateOf(0) }
    //var lastClickDate = remember { LocalDate.now() }
    Button(
        onClick = {
            // Pass the old date and days in row and get new days in a row
            //daysInARow.value = checkDates(lastClickDate, daysInARow.value)
            daysInARow.value ++
            if (daysInARow.value > 7){
                daysInARow.value = 1
            }
            // Enter new days in a row to database
            // Delete old date in database and set new one as today
            showCoupon.value = true
        }) {
        Text("Test Coupon")
    }
    if (showCoupon.value){
        showCoupon.value = couponIncentive(daysInARow.value)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun checkDates(oldDate : LocalDate, daysInARow: Int) : Int{
    val currentDate = LocalDate.now()
    var newDaysInARow = daysInARow
    if (currentDate != oldDate) {
        if (currentDate.isAfter(oldDate)) {
            newDaysInARow++
        } else {
            newDaysInARow = 1
        }
    }
    return newDaysInARow
}

@Composable
fun couponIncentive(daysInARow: Int) : Boolean {
    val showDialog = remember { mutableStateOf(true) }
    if (showDialog.value){
        AlertDialog(
            onDismissRequest = {showDialog.value = false},
            title = { Text("Good job!")},
            text = { MakeCircle(daysInARow) },
            confirmButton = {
                Button(onClick = {showDialog.value = false})
                {
                    Text(text = "Close")
                }
            }
        )
    }
    return showDialog.value
}

@Composable
fun MakeCircle (daysInARow: Int){
    Column {
        CouponCode(daysInARow)
        Row{
            for (i in 1..7){
                Box(
                    modifier = if (i in 1..daysInARow) {
                        Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    }else {
                        Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)}
                )
            }
        }
    }
}

@Composable
fun CouponCode(daysInARow: Int) {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val length = 10
    val next = Random()
    val myCode = StringBuilder()
    for (i in 0 until length) {
        myCode.append(letters[next.nextInt(letters.length)])
    }
    if (daysInARow == targetDaysInARow){
        Text(text = "Congratulations! You have used the app for $daysInARow days in a row.")
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Your free coupon is:")
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "$myCode", color = Color.Magenta, fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(10.dp))
    } else {
        Text(text = "You have used the app for $daysInARow day(s) in a row.")
        Spacer(modifier = Modifier.padding(10.dp))
    }
}