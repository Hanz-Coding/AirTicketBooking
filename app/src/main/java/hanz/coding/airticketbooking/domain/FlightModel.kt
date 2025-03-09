package hanz.coding.airticketbooking.domain

data class FlightModel(
    var airlineLogo: String = "",
    var airlineName: String = "",
    var arriveTime: String = "",
    var classSeat: String = "",
    var date: String = "",
    var from: String = "",
    var fromShort: String = "",
    var numberSeat: Int,
    var price: Double,
    var passenger: String = "",
    var seats: String = "",
    var reservedSeats: String = "",
    var time: String = "",
    var to: String = "",
    var toShort: String = ""
)
