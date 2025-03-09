package hanz.coding.airticketbooking.presentation.dashboard.state

import hanz.coding.airticketbooking.domain.LocationModel

data class DashboardState(
    val locations: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false
)
