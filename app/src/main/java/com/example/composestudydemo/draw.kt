package com.example.composestudydemo

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.transform
import com.example.composestudydemo.ui.theme.ComposeStudyDemoTheme
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview2() {
    ComposeStudyDemoTheme {
        Greeting2("Android")
    }
}

///圆形加载进度组件
//@Preview
@Composable
fun CircleLoadingIndicator() {
    val sweepAngle by remember {
        mutableStateOf(168f)
    }
    Box(Modifier
        .size(375.dp)
        .padding(30.dp)
        .drawBehind {

            val circleCenter = Offset(drawContext.size.width / 2f, drawContext.size.height / 2f)
            drawCircle(
                color = Color(0xFF1E7171),
                center = circleCenter,
                style = Stroke(width = 20.dp.toPx())
            )
            drawArc(
                color = Color(0xFF3BDCCE),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)

            )
        }
        .drawWithCache {
            onDrawBehind {

            }
            onDrawWithContent {
                drawContext.canvas.nativeCanvas.drawText(
                    "test",
                    drawContext.size.width / 2f - 100,
                    drawContext.size.height / 2f - 150,
                    android.graphics
                        .Paint()
                        .apply {
                            color = Color.White.toArgb()
                            textSize = 40.sp.toPx()
                            isFakeBoldText = true
                        })
                this.drawContent()
            }
        }, contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Loading",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "45%",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        /*Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)

        ) {


        }*/
    }
}

//绘制波浪加载
//@Preview
@Composable
fun BLLoading() {

    val adnimates = listOf(1f, 0.75f, 0.5f).map {
        rememberInfiniteTransition().animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween((it * 1f).roundToInt()),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.bg)
    Canvas(modifier = Modifier.size(360.dp)) {

        drawImage(imageBitmap, colorFilter = run {
            val cm = ColorMatrix().apply { setToSaturation(0f) }
            ColorFilter.colorMatrix(cm)
        })


        adnimates.forEachIndexed { index, anim ->
            val maxWidth = 2 * size.width
            val offsetX = maxWidth / 2 * (1 - anim.value)
            translate(-offsetX) {
                drawPath(
                    path = buildWavePath(
                        width = maxWidth,
                        height = size.height,
                        amplitude = size.height * 1f,
                        progress = 0.5f
                    ),
                    brush = ShaderBrush(ImageShader(imageBitmap).apply {
                        transform { postTranslate(offsetX, 0f) }
                    }),
                    alpha = if (index == 0) 1f else 0.5f
                )
            }
        }


        /*drawPath(
            path = buildWavePath(
                width = size.width,
                height = size.height,
                amplitude = size.height * waveConfig.amplitude,
                progress = waveConfig.progress
            ),
            brush = ShaderBrush(ImageShader(imageBitmap)),
            alpha = 0.5f
        )*/
    }
}

fun buildWavePath(width: Float, height: Float, amplitude: Float, progress: Float): Path {
    val adjustHeight = min(height * max(0f, 1 - progress), amplitude)

    val adjustWidth = 2 * width
    val dp = 2
    return Path().apply {
        reset()
        moveTo(0f, height)
        lineTo(0f, height * (1 - progress))
        if (progress > 0f && progress < 1f) {
            if (adjustHeight > 0) {
                var x = dp
                while (x < adjustWidth) {
                    lineTo(
                        x.toFloat(),
                        height * (1 - progress) - adjustHeight / 2f * sin(4.0 * Math.PI * x / adjustWidth).toFloat()
                    )
                }
                x += dp
            }
        }
        lineTo(adjustWidth, height * (1 - progress))
        lineTo(adjustWidth, height)
        close()
    }


}

