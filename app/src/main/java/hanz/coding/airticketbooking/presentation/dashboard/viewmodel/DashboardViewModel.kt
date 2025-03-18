package hanz.coding.airticketbooking.presentation.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hanz.coding.airticketbooking.domain.repository.MainRepository
import hanz.coding.airticketbooking.presentation.dashboard.state.DashboardState
import hanz.coding.airticketbooking.presentation.dashboard.state.DefaultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: MainRepository) : ViewModel() {

    val flightState = repository.getFlightState()

    private val _defaultState = MutableStateFlow(
        DefaultState(
            origin = flightState.origin,
            destination = flightState.destination,
            adultPassenger = flightState.adultPassenger,
            childPassenger = flightState.childPassenger,
            classes = flightState.classes
        )
    )
    val defaultState = _defaultState.asStateFlow()

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    fun loadLocation() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            repository.loadLocation().collect { locations ->
                _state.update {
                    it.copy(
                        locations = locations,
                        isLoading = false
                    )
                }
            }
        }
    }

}