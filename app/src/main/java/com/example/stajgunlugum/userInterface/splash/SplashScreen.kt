package com.example.stajgunlugum.userInterface.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.ui.theme.DarkGreen
import com.example.stajgunlugum.ui.theme.MainGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {

    val logoScale = remember {
        Animatable(0.45f)
    }

    val logoAlpha = remember {
        Animatable(0f)
    }

    val titleAlpha = remember {
        Animatable(0f)
    }

    val titleOffset = remember {
        Animatable(35f)
    }

    val subtitleAlpha = remember {
        Animatable(0f)
    }

    val progress = remember {
        Animatable(0f)
    }


    LaunchedEffect(Unit) {

        launch {

            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 400
                )
            )
        }


        launch {

            logoScale.animateTo(
                targetValue = 1f,

                animationSpec = spring(
                    dampingRatio =
                        Spring.DampingRatioMediumBouncy,

                    stiffness =
                        Spring.StiffnessLow
                )
            )
        }


        delay(400)


        launch {

            titleAlpha.animateTo(
                targetValue = 1f,

                animationSpec = tween(
                    durationMillis = 500
                )
            )
        }


        launch {

            titleOffset.animateTo(
                targetValue = 0f,

                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
        }


        delay(250)


        subtitleAlpha.animateTo(
            targetValue = 1f,

            animationSpec = tween(
                durationMillis = 400
            )
        )


        launch {

            progress.animateTo(
                targetValue = 1f,

                animationSpec = tween(
                    durationMillis = 1300,
                    easing = LinearEasing
                )
            )
        }


        delay(1500)

        onFinished()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DarkGreen,
                        MainGreen,
                        Color(0xFF2F8F83)
                    ),
                    start = Offset.Zero,
                    end = Offset(
                        x = 1200f,
                        y = 1800f
                    )
                )
            )
    ) {


        // ARKA PLAN DEKORASYONU

        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    translationX = 90f
                    translationY = -80f
                    alpha = 0.10f
                }
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )


        Box(
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.BottomStart)
                .graphicsLayer {
                    translationX = -60f
                    translationY = 60f
                    alpha = 0.08f
                }
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 32.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {


            // LOGO

            Box(
                modifier = Modifier
                    .size(116.dp)
                    .graphicsLayer {

                        scaleX = logoScale.value
                        scaleY = logoScale.value

                        alpha = logoAlpha.value
                    }
                    .shadow(
                        elevation = 18.dp,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(
                        Color.White.copy(
                            alpha = 0.96f
                        )
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Description,

                    contentDescription =
                        "Staj Günlüğüm",

                    tint = DarkGreen,

                    modifier =
                        Modifier.size(58.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )


            // BAŞLIK

            Text(
                text = "Staj Günlüğüm",

                modifier =
                    Modifier.graphicsLayer {

                        alpha =
                            titleAlpha.value

                        translationY =
                            titleOffset.value
                    },

                color = Color.White,

                fontSize = 31.sp,

                fontWeight =
                    FontWeight.ExtraBold,

                textAlign =
                    TextAlign.Center,

                letterSpacing = 0.2.sp
            )


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            // ALT BAŞLIK

            Text(
                text =
                    "Faaliyetlerini kaydet,\nraporlarını yapay zekâ ile oluştur.",

                modifier =
                    Modifier.graphicsLayer {

                        alpha =
                            subtitleAlpha.value
                    },

                color =
                    Color.White.copy(
                        alpha = 0.84f
                    ),

                fontSize = 15.sp,

                lineHeight = 21.sp,

                textAlign =
                    TextAlign.Center,

                fontWeight =
                    FontWeight.Medium
            )


            Spacer(
                modifier =
                    Modifier.height(42.dp)
            )


            // LOADING BAR ARKA PLAN

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(5.dp)
                    .clip(
                        RoundedCornerShape(
                            100.dp
                        )
                    )
                    .background(
                        Color.White.copy(
                            alpha = 0.22f
                        )
                    )
            ) {


                // LOADING BAR

                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            progress.value
                        )
                        .height(5.dp)
                        .clip(
                            RoundedCornerShape(
                                100.dp
                            )
                        )
                        .background(
                            Color.White
                        )
                )
            }
        }


        Text(
            text = "AI destekli staj asistanı",

            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(
                    bottom = 30.dp
                ),

            color =
                Color.White.copy(
                    alpha = 0.55f
                ),

            fontSize = 12.sp,

            fontWeight =
                FontWeight.Medium
        )
    }
}


