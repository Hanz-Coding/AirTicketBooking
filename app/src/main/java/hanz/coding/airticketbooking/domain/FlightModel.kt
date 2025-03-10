package hanz.coding.airticketbooking.domain

data class FlightModel(
    var airlineLogo: String = "",
    var airlineName: String = "",
    var arriveTime: String = "",
    var classSeat: String = "",
    var date: String = "",
    var from: String = "",
    var fromShort: String = "",
    var numberSeat: Int = 0,
    var price: Double = 0.0,
    var passenger: String = "",
    var seats: String = "",
    var reservedSeats: String = "",
    var time: String = "",
    var to: String = "",
    var toShort: String = ""
)
