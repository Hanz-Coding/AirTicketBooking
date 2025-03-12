package hanz.coding.airticketbooking.presentation.seat_select

import androidx.lifecycle.ViewModel
import hanz.coding.airticketbooking.domain.FlightModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.toImmutableList

class SeatViewModel : ViewModel() {
    private val _state = MutableStateFlow(SeatState())
    val state = _state.asStateFlow()

    private lateinit var flight: FlightModel

    fun updateFlight(item: FlightModel) {
        flight = item
        val seatList = generateSeatList(flight)
        _state.update {
            println("hanz12 updateFlight")
            it.copy(seatList = seatList)
        }
    }

    fun onAction(action: SEATACTION, seat: Seat) {
        when (action) {
            SEATACTION.ADD_SEAT -> {
                _state.update {
                    val list = it.selectedSeatName.toMutableList()
                    val seats = it.seatList.toMutableList()
                    seats.find { it.name == seat.name }?.let { item ->
                        val index = seats.indexOf(item)
                        seats[index] = item.copy(status = SeatStatus.SELECTED)
                    }
                    println("hanz12 ADD_SEAT $seats")
                    it.copy(
                        seatList = seats,
//                        selectedSeatName = list.apply { this.add(seat.name) },
//                        price = it.selectedSeatName.size * flight.price
                    ).also { println("hanz12 ADD_SEATLIST  ${it.seatList}") }
                }
            }

            SEATACTION.REMOVE_SEAT -> {
                _state.update {
                    val list = it.selectedSeatName.toMutableList()
                    println("hanz1 REMOVE_SEAT $seat")
                    it.copy(
                        seatList = it.seatList.map { item ->
                            if (item.name == seat.name) item.copy(status = SeatStatus.AVAILABLE) else item
                        },
                        selectedSeatName = list.apply { this.remove(seat.name) },
                        price = it.selectedSeatName.size * flight.price
                    )
                }
            }
        }
    }

    fun generateSeatList(flight: FlightModel): List<Seat> {
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