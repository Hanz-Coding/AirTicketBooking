package hanz.coding.airticketbooking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import hanz.coding.airticketbooking.MainAppState
import hanz.coding.airticketbooking.presentation.dashboard.navigation.MainRoute
import hanz.coding.airticketbooking.presentation.dashboard.navigation.dashboardScreen
import hanz.coding.airticketbooking.presentation.search.navigateToSearch
import hanz.coding.airticketbooking.presentation.search.searchScreen
import hanz.coding.airticketbooking.presentation.seat_select.navigateToSeat
import hanz.coding.airticketbooking.presentation.seat_select.seatScreen
import hanz.coding.airticketbooking.presentation.ticket.navigateToTicket
import hanz.coding.airticketbooking.presentation.ticket.ticketScreen

@Composable
fun TicketNavHost(
    appState: MainAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = MainRoute,
        modifier = modifier
    ) {
        dashboardScreen(
            onConfirmClick = { from, to ->
                navController.navigateToSearch(
                    destinationFrom = from,
                    destinationTo = to
                )
            },
            onShowSnackbar = onShowSnackbar
        )

        searchScreen(
            onBackClick = navController::popBackStack,
            onFlightClick = { navController.navigateToSeat() }
        )

        seatScreen(
            onBackClick = navController::popBackStack,
            onConfirmClick = { navController.navigateToTicket() }
        )

        ticketScreen(
            onBackClick = navController::popBackStack,
            onConfirmClick = { }
        )
    }
}