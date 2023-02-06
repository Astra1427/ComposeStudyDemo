package com.example.composestudydemo.anim.practice

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestudydemo.ui.theme.Shapes


/*
* 定义一个包含三种灰度颜色的列表shimmerColors，
* 然后基于此颜色列表创建带有渐变效果的Brush。
* 接下来只要以动画的方式改变LinearGradient中颜色的分布位置，
* 就可以实现微光的动画效果。
* */
val shimmerColors = listOf(
    Color.LightGray.copy(alpha = 0.6f),
    Color.LightGray.copy(alpha = 0.2f),
    Color.LightGray.copy(alpha = 0.6f),
)


///骨架屏的动画效果
@Preview
@Composable
fun MainPractice() {
    // 首先我们希望这个动画能够无限循环播放，
    // 所以使用rememberInfiniteTransition定义了一个InfiniteTransition，
    // 并使用animateFloat创建一个可以随动画改变的Float状态translateAnim。
    val transition = rememberInfiniteTransition()
    // 首先我们希望这个动画能够无限循环播放，
    // 所以使用rememberInfiniteTransition定义了一个InfiniteTransition，
    // 并使用animateFloat创建一个可以随动画改变的Float状态translateAnim。
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    //创建Brush时，使用此translateAnim定义LinearGradient
    //end坐标的位置随着动画改变，渐变色产生动画位移效果
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, translateAnim.value)
    )
    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerItem(brush: Brush) {

    val barHeight = 10.dp;
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    val spacerPadding = PaddingValues(vertical = 5.dp, horizontal = 5.dp)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.Center) {
                repeat(5){
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
                    Spacer(modifier = Modifier
                        .height(barHeight)
                        .clip(roundedCornerShape)
                        .fillMaxWidth(0.7f)
                        .background(brush))
                }
                Spacer(modifier = Modifier.padding(spacerPadding))
            }
            Spacer(modifier = Modifier.width(10.dp) )
            Spacer(modifier = Modifier
                .size(100.dp)
                .clip(roundedCornerShape)
                .background(brush))
        }
        repeat(3){
            Spacer(modifier = Modifier.padding(spacerPadding))
            Spacer(modifier = Modifier
                .height(barHeight)
                .clip(roundedCornerShape)
                .fillMaxWidth()
                .background(brush))
            Spacer(modifier = Modifier.padding(spacerPadding))
        }
    }
}

