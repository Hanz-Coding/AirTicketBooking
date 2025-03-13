package hanz.coding.airticketbooking.presentation.seat_select

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import hanz.coding.airticketbooking.presentation.ticket.TicketDetailActivity
import org.koin.androidx.compose.koinViewModel

@SuppressLint("RestrictedApi")
class SeatSelectActivity : ComponentActivity() {
    private lateinit var flight: FlightModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        flight = intent.getSerializableExtra("flight", FlightModel::class.java) as FlightModel
        setContent {
//            StatusBarColor()
            val context = LocalContext.current
            val viewModel = koinViewModel<SeatViewModel>()
            LaunchedEffect(true) {
                viewModel.updateFlight(flight)
            }
            val state by viewModel.state.collectAsStateWithLifecycle()
            SeatListScreen(
                state = state,
                onBackClick = { finish() },
                onConfirm = {
                    val intent = Intent(this, TicketDetailActivity::class.java).apply {
                        putExtra("flight", flight)
                    }
                    context.startActivity(intent, null)
                },
                onAction = viewModel::onAction
            )
        }
    }
}