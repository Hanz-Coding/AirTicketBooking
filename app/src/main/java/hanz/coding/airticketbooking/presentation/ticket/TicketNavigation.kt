package hanz.coding.airticketbooking.presentation.ticket

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TicketRoute

fun NavController.navigateToTicket(navOptions: NavOptions? = null) =
    navigate(TicketRoute, navOptions)

fun NavGraphBuilder.ticketScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    composable<TicketRoute> {
        TicketRoot(
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick
        )
    }
}