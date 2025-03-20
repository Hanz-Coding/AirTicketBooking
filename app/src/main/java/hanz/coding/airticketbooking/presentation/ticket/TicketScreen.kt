package hanz.coding.airticketbooking.presentation.ticket

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.koinViewModel


@Composable
fun TicketRoot(
    modifier: Modifier = Modifier,
    ticketViewModel: TicketViewModel = koinViewModel<TicketViewModel>(),
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    StatusBarColor()
    val flight = ticketViewModel.getCurrentFlight()
    TicketDetailScreen(
        flight = flight,
        onBackClick = onBackClick,
        onDownloadTicketClick = onConfirmClick
    )
}