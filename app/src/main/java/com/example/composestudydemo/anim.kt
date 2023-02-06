package com.example.composestudydemo

import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityDemo() {
    val editable by remember {
        mutableStateOf(true)
    }
    val visible by remember {
        mutableStateOf(true)
    }
    val density = LocalDensity.current
    var count by remember { mutableStateOf(0) }
    Column() {
        AnimatedVisibility(visible = visible, enter = slideInVertically {
            with(density) {
                40.dp.roundToPx()
            }
        } + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
            Text(
                text = "Hello",
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Button(
            modifier =  Modifier.animateContentSize (),
            onClick = {
                count++
            }) {
            Text(text = "Count: $count")

        }
        AnimatedContent(targetState = count) { targetCount ->
            Text(text = "Count: $targetCount")
        }

    }

    var test = (slideInHorizontally() + fadeIn()).with(slideOutHorizontally() + fadeOut())
    var test2 = slideInHorizontally() + fadeIn() with slideOutHorizontally() + fadeOut()

}

