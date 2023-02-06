package com.example.composestudydemo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AnimAsStateDemo(){
    var change by remember { mutableStateOf(false)}
    var flag by remember { mutableStateOf(false)}
    val buttonSize by animateDpAsState(
        targetValue = if(change) 32.dp else 24.dp
    )
    val buttonColor by animateColorAsState(
        targetValue = if(flag) Color.Red else Color.Gray
    )
    if(buttonSize == 32.dp){
        change = false
    }
    IconButton(onClick = {
        change =  false
        flag = !flag
    }) {
        Icon(Icons.Rounded.Star,
        contentDescription = null ,
        modifier = Modifier.size(buttonSize),
        tint = buttonColor)
    }

}