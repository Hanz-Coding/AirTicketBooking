package hanz.coding.airticketbooking.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hanz.coding.airticketbooking.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MainRepository) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun loadFlights(from: String, to: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            repository.loadFlights(from, to).collect { flights ->
                _state.update {
                    it.copy(
                        flights = flights,
                        isLoading = false
                    )
                }
            }
        }
    }
}