package com.example.stajgunlugum.userInterface.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
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
import com.example.stajgunlugum.data.local.ReportEntity
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    reports: List<ReportEntity>,
    onBackClick: () -> Unit = {},
    onReportClick: (ReportEntity) -> Unit = {},
    onDeleteReport: (ReportEntity) -> Unit = {}
) {

    Scaffold(
        containerColor = PageBackground,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Raporlar",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri",
                            tint = PrimaryText
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { innerPadding ->

        if (reports.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = SecondaryText
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "Henüz oluşturulmuş rapor yok.",
                    color = SecondaryText,
                    fontSize = 15.sp
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )
                }

                items(
                    items = reports,
                    key = { report ->
                        report.id
                    }
                ) { report ->

                    ReportHistoryCard(

                        report = report,

                        onClick = {
                            onReportClick(report)
                        },

                        onDeleteClick = {
                            onDeleteReport(report)
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun ReportHistoryCard(
    report: ReportEntity,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = "Rapor",
                tint = MainGreen
            )

            Spacer(
                modifier = Modifier.padding(6.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = report.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = report.createdAt,
                    fontSize = 13.sp,
                    color = SecondaryText
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text =
                        if (report.language == "en") {
                            "İngilizce Rapor"
                        } else {
                            "Türkçe Rapor"
                        },

                    fontSize = 12.sp,
                    color = MainGreen
                )
            }


            IconButton(
                onClick = {
                    onDeleteClick()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Raporu Sil",
                    tint = Color.Red
                )
            }
        }
    }
}