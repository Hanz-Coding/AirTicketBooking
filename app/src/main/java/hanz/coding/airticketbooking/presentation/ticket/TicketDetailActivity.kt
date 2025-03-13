package hanz.coding.airticketbooking.presentation.ticket

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor

class TicketDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val flight = intent.getSerializableExtra("flight", FlightModel::class.java) as FlightModel
        setContent {
//            StatusBarColor()

            TicketDetailScreen(
                flight = flight,
                onBackClick = { finish() },
                onDownloadTicketClick = {

                }
            )
        }

    }
}