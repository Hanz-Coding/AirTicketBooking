package hanz.coding.airticketbooking.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object AppGraph : Routes
}