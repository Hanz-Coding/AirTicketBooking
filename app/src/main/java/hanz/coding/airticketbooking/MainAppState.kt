package hanz.coding.airticketbooking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): MainAppState {
    return remember(navController) {
        MainAppState(navController = navController)
    }
}

class MainAppState(
    val navController: NavHostController
) {

}

