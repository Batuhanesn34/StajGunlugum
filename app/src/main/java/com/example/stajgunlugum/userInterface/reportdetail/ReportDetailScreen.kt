package com.example.stajgunlugum.userInterface.reportdetail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.data.local.ReportEntity
import com.example.stajgunlugum.ui.theme.DarkGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailScreen(
    report: ReportEntity,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    val clipboardManager =
        LocalClipboardManager.current

    val context =
        LocalContext.current


    Scaffold(
        containerColor = PageBackground,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Rapor Detayı",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Text(
                text = report.title,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )

            Spacer(
                modifier = Modifier.height(8.dp)
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

                fontSize = 13.sp,
                color = SecondaryText
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(14.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {

                    Text(
                        text = "Rapor Metni",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    HorizontalDivider()

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = report.reportText,
                        fontSize = 15.sp,
                        lineHeight = 23.sp,
                        color = PrimaryText
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // KOPYALA + PAYLAŞ

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                OutlinedButton(

                    onClick = {

                        clipboardManager.setText(
                            AnnotatedString(
                                report.reportText
                            )
                        )

                        Toast.makeText(
                            context,
                            "Rapor metni kopyalandı.",
                            Toast.LENGTH_SHORT
                        ).show()
                    },

                    modifier = Modifier.weight(1f)
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ContentCopy,

                        contentDescription =
                            "Kopyala"
                    )

                    Spacer(
                        modifier =
                            Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Kopyala"
                    )
                }


                OutlinedButton(

                    onClick = {

                        val shareIntent =
                            Intent(
                                Intent.ACTION_SEND
                            ).apply {

                                type = "text/plain"

                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    report.title
                                )

                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    report.reportText
                                )
                            }


                        context.startActivity(

                            Intent.createChooser(
                                shareIntent,
                                "Raporu paylaş"
                            )
                        )
                    },

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Share,

                        contentDescription =
                            "Paylaş"
                    )

                    Spacer(
                        modifier =
                            Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Paylaş"
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )



            // SİL


            Button(

                onClick =
                    onDeleteClick,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                shape =
                    RoundedCornerShape(12.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Color.Red,

                        contentColor =
                            Color.White
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        null
                )

                Spacer(
                    modifier =
                        Modifier.padding(4.dp)
                )

                Text(
                    text = "Raporu Sil",
                    fontWeight =
                        FontWeight.SemiBold
                )
            }


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )
        }
    }
}