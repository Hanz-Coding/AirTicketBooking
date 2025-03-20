package hanz.coding.airticketbooking.presentation.seat_select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeatScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    StatusBarColor()
    val viewModel = koinViewModel<SeatViewModel>()
    LaunchedEffect(true) {
        viewModel.updateFlight()
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    SeatScreen(
        state = state,
        onBackClick = onBackClick,
        onConfirm = onConfirmClick,
        onAction = viewModel::onAction
    )
}

