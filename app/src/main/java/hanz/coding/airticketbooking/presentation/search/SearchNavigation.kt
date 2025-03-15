package hanz.coding.airticketbooking.presentation.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SearchRoute(
    val destinationFrom: String,
    val destinationTo: String,
)

fun NavController.navigateToSearch(
    destinationFrom: String,
    destinationTo: String,
    navOptions: NavOptions? = null
) =
    navigate(
        SearchRoute(
            destinationFrom = destinationFrom,
            destinationTo = destinationTo
        ), navOptions
    )

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
) {
    composable<SearchRoute> {
        val args = it.toRoute<SearchRoute>()
        SearchRoot(
            destinationFrom = args.destinationFrom,
            destinationTo = args.destinationTo,
            onBackClick = onBackClick
        )
    }
}