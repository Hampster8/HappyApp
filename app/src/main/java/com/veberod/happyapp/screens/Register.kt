package com.veberod.happyapp.screens

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp


@Composable
fun Register() {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = firstname,
            onValueChange = { newText -> firstname = newText },
            label = { Text(text = "First name") },
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        TextField(
            value = lastname,
            onValueChange = { newText -> lastname = newText },
            label = { Text(text = "Last name") },
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )

        )

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = age,
            onValueChange = { newText -> age = newText },
            label = { Text(text = "Age") },
            modifier = Modifier.padding(8.dp)
        )

        MyUI()


        TextField(
            value = username,
            onValueChange = { newText -> username = newText },
            label = { Text(text = "Username") },
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        TextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text(text = "Password") },
            modifier = Modifier.padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        TextField(
            value = confirmPassword,
            onValueChange = { newText -> confirmPassword = newText },
            label = { Text(text = "Confirm password") },
            modifier = Modifier.padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        Button(onClick = {

            if (password == confirmPassword ) {
                //Toast.makeText(context,"Password matches",Toast.LENGTH_SHORT)
            } else {
                //Toast.makeText(context,"Password does not match",Toast.LENGTH_SHORT)
            }
        }, modifier = Modifier.height(43.dp).width(100.dp)) {
            Text(text = "Register", fontSize = 14.sp)
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyUI() {

    val contextForToast = LocalContext.current.applicationContext
    val listItems = arrayOf("Female", "Male", "Non-binary", "Prefer not to respond")


    // state of the menu options
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = {Text(text = "Gender") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT)
                    .show()
                    expanded = false
                }) {
                    Text(text = selectedOption)

                }

            }
        }

      }
   }










/*@Composable
fun SimpleButton() {
    Button(onClick = {

        //your onclick code here
    }) {
        Text(text = "Register")
    }
}*/



