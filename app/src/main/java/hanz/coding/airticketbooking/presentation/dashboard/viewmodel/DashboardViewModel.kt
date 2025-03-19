package hanz.coding.airticketbooking.presentation.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hanz.coding.airticketbooking.domain.repository.MainRepository
import hanz.coding.airticketbooking.presentation.dashboard.state.ACTION
import hanz.coding.airticketbooking.presentation.dashboard.state.DashboardState
import hanz.coding.airticketbooking.presentation.dashboard.state.DefaultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: MainRepository) : ViewModel() {

    private val _defaultState = MutableStateFlow(DefaultState())
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

    fun onAction(action: ACTION) {
        when (action) {
            is ACTION.UPDATE_ADULT -> _defaultState.update {
                it.copy(adultPassenger = action.adultPassenger)
            }

            is ACTION.UPDATE_CHILD -> _defaultState.update {
                it.copy(childPassenger = action.childPassenger)
            }

            is ACTION.UPDATE_CLASS -> _defaultState.update {
                it.copy(classes = action.classes)
            }

            is ACTION.UPDATE_DESTINATION -> _defaultState.update {
                it.copy(destination = action.destination)
            }

            is ACTION.UPDATE_ORIGIN -> _defaultState.update {
                it.copy(origin = action.origin)
            }
        }
    }
}