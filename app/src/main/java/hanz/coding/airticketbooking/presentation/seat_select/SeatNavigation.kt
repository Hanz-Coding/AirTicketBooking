package hanz.coding.airticketbooking.presentation.seat_select

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SeatRoute

fun NavController.navigateToSeat(navOptions: NavOptions? = null) = navigate(SeatRoute, navOptions)

fun NavGraphBuilder.seatScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    composable<SeatRoute> {
        SeatScreenRoot(
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick
        )
    }
}