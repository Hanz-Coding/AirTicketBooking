package hanz.coding.airticketbooking.presentation.ticket

import androidx.lifecycle.ViewModel
import hanz.coding.airticketbooking.domain.repository.MainRepository

class TicketViewModel(private val repository: MainRepository) : ViewModel() {
    fun getCurrentFlight() = repository.getCurrentFlight()
}