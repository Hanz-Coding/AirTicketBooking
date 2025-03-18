package hanz.coding.airticketbooking.presentation.dashboard.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val label: String, val icon: ImageVector
) {
    HOME("Home", Icons.Default.Home),
    CART("Cart", Icons.Default.ShoppingCart),
    FAVOURITE("Favourite", Icons.Default.Favorite),
    ORDER("Order", Icons.Default.Person)
}
