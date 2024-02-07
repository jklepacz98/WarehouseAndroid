package com.example.warehouseandroid.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ErrorToast(errorMessage: String?) {
    val context = LocalContext.current
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            //todo
            Log.e("Error123", it)
        }
    }
}