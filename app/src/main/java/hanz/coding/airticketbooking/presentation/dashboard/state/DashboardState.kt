package hanz.coding.airticketbooking.presentation.dashboard.state

import hanz.coding.airticketbooking.domain.LocationModel

data class DashboardState(
    val locations: List<LocationModel> = emptyList(),
    val isLoading: Boolean = false,
    val defaultState: DefaultState = DefaultState()
)

data class DefaultState(
    val origin: String = "Select origin",
    val destination: String = "Select destination",
    val adultPassenger: Int = 0,
    val childPassenger: Int = 0,
    val classes: String = "Select class"
)

sealed class ACTION {
    data class UPDATE_ORIGIN(val origin: String) : ACTION()
    data class UPDATE_DESTINATION(val destination: String) : ACTION()
    data class UPDATE_ADULT(val adultPassenger: Int) : ACTION()
    data class UPDATE_CHILD(val childPassenger: Int) : ACTION()
    data class UPDATE_CLASS(val classes: String) : ACTION()
}