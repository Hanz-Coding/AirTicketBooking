package hanz.coding.airticketbooking.presentation.search

import hanz.coding.airticketbooking.domain.FlightModel

data class SearchState(
    val flights: List<FlightModel> = emptyList(),
    val isLoading: Boolean = false
)

val tempFlightModel = FlightModel(
    airlineLogo = "https://res.cloudinary.com/delhu0pyy/image/upload/v1741312753/DeltaAirline_x7vmce.png",
    airlineName = "Delta Airlines",
    arriveTime = "2h 45m",
    classSeat = "Business class",
    date = "24 aug,2024",
    from = "NewYork",
    fromShort = "JFK",
    numberSeat = 79,
    price = 170.6,
    reservedSeats = "D1,B3,B3,F6,E4,D1,D5,A6,A8,E7,F9,D11",
    time = "12:54",
    to = "LosAngles",
    toShort = "LAX",
)
