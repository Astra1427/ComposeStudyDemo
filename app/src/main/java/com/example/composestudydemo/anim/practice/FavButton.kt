package com.example.composestudydemo.anim.practice

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composestudydemo.ui.theme.Purple500

// 实现方式1：高级别API(AnimatedContent)
//首先，针对两种状态进行定义：
data class UiState(
    val backgroundColor: Color,
    val textColor: Color,
    val roundedCorner: Int,
    val buttonWidth: Dp
)

enum class ButtonState(val ui: UiState) {
    Idle(UiState(Purple500, Color.White, 60, 60.dp)),
    Pressed(UiState(Color.White, Purple500, 6, 300.dp)),
}

@Preview
@ExperimentalAnimationApi
@Composable
fun AnimatedFavButton(modifier: Modifier = Modifier) {
    var buttonState by remember {
        mutableStateOf(ButtonState.Idle)
    }
    Box(modifier) {
        AnimatedContent(targetState = buttonState,
            transitionSpec = {
                fadeIn(tween(durationMillis = 3000)) with
                        fadeOut(tween(durationMillis = 3000)) using
                        SizeTransform { initialState, targetState ->
                            tween(
                                durationMillis = 3000
                            )
                        }
            }) { state ->
            Button(

                modifier = Modifier
                    .width(state.ui.buttonWidth)
                    .height(60.dp)
                    .clip(RoundedCornerShape(state.ui.roundedCorner)),
                colors = ButtonDefaults.buttonColors(backgroundColor = state.ui.backgroundColor),
                onClick = {
                    buttonState =
                        if (state == ButtonState.Idle) ButtonState.Pressed else ButtonState.Idle
                }) {
                if (state == ButtonState.Idle) {
                    //圆形Icon

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = state.ui.textColor,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    //圆角矩形附带文字 Icon
                    Row {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = state.ui.textColor,
                            modifier = Modifier
                                .size(24.dp)
                                .align(CenterVertically),

                            )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "ADD TO FAVORITES!",
                            softWrap = false,
                            modifier = Modifier.align(CenterVertically),
                            color = state.ui.textColor
                        )
                    }
                }
            }
        }
    }
}

//实现方式2：低级别API(updateTransition)
@Preview
@ExperimentalAnimationApi
@Composable
fun AnimatedFavButton2(modifier: Modifier = Modifier) {
    var buttonState by remember {
        mutableStateOf(ButtonState.Idle)
    }
    val transition = updateTransition(targetState = buttonState, label = "")
    val animateDuration = 3000

    //updateTransition虽然需要针对UiState中多个属性声明其对应的动画状态值，使用起来略显烦琐，
    // 但是它为按钮状态的切换带来了更加精细的转场动画设置能力，有助于打造更极致的用户体验。
    val backgroundColor by transition.animateColor(transitionSpec = {
        tween(durationMillis = animateDuration)
    }, label = "") { it.ui.backgroundColor }

    val textColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis = animateDuration)
        }, label = ""
    ) { it.ui.textColor }

    val buttonWidth by transition.animateDp(transitionSpec = {
        tween(durationMillis = animateDuration)
    }, label = "") { it.ui.buttonWidth }

    val roundedCorner by transition.animateInt(transitionSpec = {
        tween(durationMillis = animateDuration)
    }, label = "") { it.ui.roundedCorner }


    Box(modifier = modifier) {
        Button(

            modifier = Modifier
                .width(buttonWidth)
                .height(60.dp)
                .clip(RoundedCornerShape(roundedCorner)),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
            onClick = {
                buttonState =
                    if (buttonState == ButtonState.Idle) ButtonState.Pressed else ButtonState.Idle
            }) {
            if (buttonState == ButtonState.Idle) {
                //圆形Icon

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                //圆角矩形附带文字 Icon
                Row {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier
                            .size(24.dp)
                            .align(CenterVertically),

                        )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "ADD TO FAVORITES!2",
                        softWrap = false,
                        modifier = Modifier.align(CenterVertically),
                        color = textColor
                    )
                }
            }
        }
    }
}

