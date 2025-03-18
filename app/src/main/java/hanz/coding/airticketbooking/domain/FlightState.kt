package hanz.coding.airticketbooking.domain

import hanz.coding.airticketbooking.presentation.seat_select.Seat

data class FlightState(
    var seatList: List<Seat> = emptyList(),
    var nameList: List<String> = emptyList(),
    var price: Double = 0.0
)

fun FlightState.updateSeatList(list: List<Seat>) {
    this.seatList = list
}
