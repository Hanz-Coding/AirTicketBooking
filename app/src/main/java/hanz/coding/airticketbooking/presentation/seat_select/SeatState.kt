package hanz.coding.airticketbooking.presentation.seat_select

data class SeatState(
    val seatList: List<Seat> = emptyList(),
    val selectedSeatName :List<String> = emptyList(),
    val price: Double = 0.0
)

enum class SEATACTION {
    ADD_SEAT,
    REMOVE_SEAT
}