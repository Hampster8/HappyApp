package com.veberod.happyapp.feature_smilies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommentPopup(onComment: (String) -> Unit, onDismiss: () -> Unit) {
    val comment = remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Comment") },
        text = { TextField(value = comment.value, onValueChange = { comment.value = it }) },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.End) {
                Button(onClick = { onComment(comment.value) }, content = { Text("Save") })
                Button(onClick = onDismiss, content = { Text("Cancel") })
            }
        }
    )
}