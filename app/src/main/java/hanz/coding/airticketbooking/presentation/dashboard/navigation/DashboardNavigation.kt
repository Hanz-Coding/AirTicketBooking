package hanz.coding.airticketbooking.presentation.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hanz.coding.airticketbooking.presentation.dashboard.DashboardRoot
import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

@Serializable
data object MainRoute

fun NavController.navigateToDashboard(navOptions: NavOptions? = null) {
    navigate(DashboardRoute, navOptions)
}

fun NavGraphBuilder.dashboardScreen(
    onConfirmClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation<MainRoute>(startDestination = DashboardRoute) {
        composable<DashboardRoute>
        {
            DashboardRoot(
                onConfirmClick = onConfirmClick,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}