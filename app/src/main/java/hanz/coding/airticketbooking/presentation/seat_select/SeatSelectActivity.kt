package hanz.coding.airticketbooking.presentation.seat_select

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
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
            val viewModel = koinViewModel<SeatViewModel>()
            viewModel.updateFlight(flight)
            val state by viewModel.state.collectAsStateWithLifecycle()
            SeatListScreen(
                state = state,
                onBackClick = { finish() },
                onConfirm = {},
                onAction = viewModel::onAction
            )
        }
    }
}