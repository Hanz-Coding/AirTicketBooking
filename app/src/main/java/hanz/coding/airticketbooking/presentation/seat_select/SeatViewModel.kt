package hanz.coding.airticketbooking.presentation.seat_select

import androidx.lifecycle.ViewModel
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SeatViewModel(repository: MainRepository) : ViewModel() {
    private val _state = MutableStateFlow(SeatState())
    val state = _state.asStateFlow()

    private val flight = repository.getCurrentFlight()
    private val seatPrice = flight?.price ?: 0.0

    fun updateFlight() {

        flight?.let {
            val seatList = generateSeatList(flight)
            _state.update {
                it.copy(seatList = seatList)
            }
        }
    }

    fun onAction(action: SeatAction, seat: Seat) {
        when (action) {
            SeatAction.ADD_SEAT -> {
                _state.update {
                    val seatCount = it.selectedSeatName.size
                    it.copy(
                        seatList = it.seatList.map { item ->
                            if (item.name == seat.name) item.copy(status = SeatStatus.SELECTED)
                            else item
                        },
                        selectedSeatName = it.selectedSeatName.toMutableList()
                            .apply { this.add(seat.name) },
                        price = (seatCount + 1) * seatPrice
                    )
                }
            }

            SeatAction.REMOVE_SEAT -> {
                _state.update {
                    val seatCount = it.selectedSeatName.size
                    it.copy(
                        seatList = it.seatList.map { item ->
                            if (item.name == seat.name) item.copy(status = SeatStatus.AVAILABLE) else item
                        },
                        selectedSeatName = it.selectedSeatName.toMutableList()
                            .apply { this.remove(seat.name) },
                        price = (seatCount - 1) * seatPrice
                    )
                }
            }
        }
    }

    private fun generateSeatList(flight: FlightModel): List<Seat> {
        val seatList = mutableListOf<Seat>()
        val numberSeat = flight.numberSeat + (flight.numberSeat / 7) + 1
        val seatAlphabetMap = mapOf(
            0 to "A",
            1 to "B",
            2 to "C",
            4 to "D",
            5 to "E",
            6 to "F",
        )
        var row = 0
        for (i in 0 until numberSeat) {
            if (i % 7 == 0)
                row++
            if (i % 7 == 3) {
                seatList.add(Seat(SeatStatus.EMPTY, row.toString()))
            } else {
                val seatName = seatAlphabetMap[i % 7] + row
                val seatStatus = if (flight.reservedSeats.contains(seatName)) {
                    SeatStatus.UNAVAILABLE
                } else {
                    SeatStatus.AVAILABLE
                }
                seatList.add(Seat(seatStatus, seatName))
            }
        }
        return seatList
    }
}