package hanz.coding.airticketbooking.presentation.seat_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.search.tempFlightModel
import hanz.coding.airticketbooking.presentation.seat_select.components.BottomSelection
import hanz.coding.airticketbooking.presentation.seat_select.components.SeatItem
import hanz.coding.airticketbooking.presentation.seat_select.components.TopSection

enum class SeatStatus {
    AVAILABLE,
    SELECTED,
    UNAVAILABLE,
    EMPTY
}

data class Seat(
    var status: SeatStatus,
    var name: String
)

@Composable
fun SeatListScreen(
    state: SeatState,
    onBackClick: () -> Unit,
    onConfirm: () -> Unit,
    onAction: (SEATACTION, Seat) -> Unit
) {
    val seatList = state.seatList
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkPurple2))
    ) {
        val (topSection, middleSection, bottomSection) = createRefs()
        TopSection(
            modifier = Modifier.constrainAs(topSection) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onBackClick = onBackClick
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 100.dp)
                .constrainAs(middleSection) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            val (airplane, searGrid) = createRefs()
            Image(
                painter = painterResource(R.drawable.airple_seat),
                contentDescription = null,
                modifier = Modifier.constrainAs(airplane) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 7),
                modifier = Modifier
                    .padding(top = 240.dp)
                    .padding(horizontal = 64.dp)
                    .constrainAs(searGrid) {
                        top.linkTo(parent.top)
                        start.linkTo(airplane.start)
                        end.linkTo(airplane.end)
                    }
            ) {
                items(
                    seatList
                ) { seat ->
                    println("hanz1 REMOVE_SEAT _ITEM $seat")
                    SeatItem(
                        seat = seat,
                        onSeatClick = {
                            when (seat.status) {
                                SeatStatus.AVAILABLE -> {
                                    seat.status = SeatStatus.SELECTED
                                    onAction(SEATACTION.ADD_SEAT, seat)
                                }

                                SeatStatus.SELECTED -> {
                                    seat.status = SeatStatus.AVAILABLE
                                    onAction(SEATACTION.REMOVE_SEAT, seat)
                                }

                                SeatStatus.UNAVAILABLE -> {

                                }

                                SeatStatus.EMPTY -> {

                                }
                            }
                        }
                    )
                }
            }
        }

        BottomSelection(
            seatCount = state.selectedSeatName.size,
            selectSeats = state.selectedSeatName.joinToString(","),
            totalPrice = state.price,
            onConfirmClick = {
                if (state.selectedSeatName.isNotEmpty()) {
                    onConfirm()
                } else {
                    //
                }
            },
            modifier = Modifier.constrainAs(bottomSection) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}


@Preview
@Composable
fun SeatListPreview(modifier: Modifier = Modifier) {
    SeatListScreen(
        state = SeatState(),
        onBackClick = {},
        onConfirm = {},
        onAction = { _, _ -> }
    )
}