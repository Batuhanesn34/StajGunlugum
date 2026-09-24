
package com.example.stajgunlugum.userInterface.allactivities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.data.local.ActivityEntity
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllActivitiesScreen(
    activities: List<ActivityEntity>,
    onBackClick: () -> Unit = {},
    onActivityClick: (ActivityEntity) -> Unit = {}
) {

    Scaffold(
        containerColor = PageBackground,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Tüm Faaliyetler",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Geri"
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            Color.White
                    )
            )
        }

    ) { innerPadding ->


        if (activities.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.History,

                    contentDescription =
                        null,

                    tint =
                        SecondaryText
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Text(
                    text =
                        "Henüz kayıtlı faaliyet yok.",

                    color =
                        SecondaryText
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        horizontal = 16.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                item {

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )
                }


                items(
                    items =
                        activities,

                    key = {
                        it.id
                    }
                ) { activity ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                onActivityClick(
                                    activity
                                )
                            },

                        shape =
                            RoundedCornerShape(
                                14.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation =
                                    2.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    16.dp
                                )
                        ) {

                            Text(
                                text =
                                    activity.title,

                                fontSize =
                                    16.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    PrimaryText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        6.dp
                                    )
                            )


                            Text(
                                text =
                                    activity.date,

                                fontSize =
                                    13.sp,

                                color =
                                    SecondaryText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        4.dp
                                    )
                            )


                            Text(
                                text =
                                    activity.category,

                                fontSize =
                                    13.sp,

                                color =
                                    MainGreen
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        4.dp
                                    )
                            )


                            Text(
                                text =
                                    formatDuration(
                                        activity.durationMinutes
                                    ),

                                fontSize =
                                    13.sp,

                                color =
                                    SecondaryText
                            )
                        }
                    }
                }


                item {

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )
                }
            }
        }
    }
}


private fun formatDuration(
    totalMinutes: Int
): String {

    val hours =
        totalMinutes / 60

    val minutes =
        totalMinutes % 60


    return when {

        hours > 0 &&
                minutes > 0 ->

            "${hours}s ${minutes}dk"

        hours > 0 ->

            "${hours}s"

        else ->

            "${minutes}dk"
    }
}