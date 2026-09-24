package com.example.stajgunlugum.userInterface.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.model.ActivityUiModel

import com.example.stajgunlugum.ui.theme.ActivityBlue
import com.example.stajgunlugum.ui.theme.ActivityOrange
import com.example.stajgunlugum.ui.theme.ActivityPurple
import com.example.stajgunlugum.ui.theme.DarkGreen
import com.example.stajgunlugum.ui.theme.LightGreen
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menüyü aç",
                tint = PrimaryText
            )
        }

        Text(
            text = "Staj Günlüğüm",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Bildirimleri göster",
                tint = PrimaryText
            )
        }
    }
}

@Composable
fun WorkingTimeCard(
    workingTime: String,
    targetTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGreen
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Bugünkü Çalışma Süresi",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = workingTime,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = targetTime,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Çalışma süresi",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun StatisticCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    valueColor: Color
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )

        }
    }
}

@Composable
fun ActivityItem(
    activity: ActivityUiModel
){
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(
                    horizontal = 15.dp,
                    vertical = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(  //benim aktivite kartımın solunda ki renkler
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = activity.color,
                        shape = CircleShape
                    )
            )
            Text(
                text = activity.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                    fontSize = 15.sp,
                    color = PrimaryText
            )
            Text(
                text = activity.duration,
                fontSize = 10.sp,
                color = SecondaryText
            )
        }

    }

}

@Composable
fun AddActivityButton(
    onClick: () -> Unit

){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGreen,
            contentColor = Color.White
        )
    ){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Yeni Faaliyet Ekle",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
@Composable
fun HomeBottomNavigation(
    onHomeClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onReportsClick: () -> Unit = {}
) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            selected = selectedItemIndex == 0,
            onClick = {
                selectedItemIndex = 0
                onHomeClick()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Ana Sayfa"
                )
            },
            label = {
                Text(text = "Ana Sayfa")
            },
            colors = bottomNavigationColors()
        )

        NavigationBarItem(
            selected = selectedItemIndex == 1,
            onClick = {
                selectedItemIndex = 1
                onCalendarClick()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Takvim"
                )
            },
            label = {
                Text(text = "Takvim")
            },
            colors = bottomNavigationColors()
        )

        NavigationBarItem(
            selected = selectedItemIndex == 2,
            onClick = {
                selectedItemIndex = 2
                onReportsClick()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Raporlar"
                )
            },
            label = {
                Text(text = "Raporlar")
            },
            colors = bottomNavigationColors()
        )
    }
}


@Composable
private fun bottomNavigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = DarkGreen,
        selectedTextColor = DarkGreen,
        indicatorColor = LightGreen,
        unselectedIconColor = SecondaryText,
        unselectedTextColor = SecondaryText
    )















