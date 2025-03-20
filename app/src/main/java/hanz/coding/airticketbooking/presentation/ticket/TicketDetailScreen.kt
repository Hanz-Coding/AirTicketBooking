package hanz.coding.airticketbooking.presentation.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.search.tempFlightModel
import hanz.coding.airticketbooking.presentation.splash.GradientButton

@Composable
fun TicketDetailScreen(
    flight: FlightModel?,
    onBackClick: () -> Unit,
    onDownloadTicketClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.darkPurple2)
            )
    )
    {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(colorResource(R.color.darkPurple2))
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.darkPurple2))
            ) {
                val (topSection, ticketDetail) = createRefs()
                TicketHeader(
                    onBackClick = onBackClick,
                    modifier = Modifier.constrainAs(topSection) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                TicketDetailContent(
                    item = flight,
                    modifier = Modifier.constrainAs(ticketDetail) {
                        top.linkTo(parent.top, margin = 110.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
            GradientButton(
                onClick = onDownloadTicketClick,
                text = stringResource(R.string.str_get_ticket)
            )
        }
    }
}

@Preview
@Composable
fun TicketScreenPreview() {
    TicketDetailScreen(
        flight = tempFlightModel,
        onDownloadTicketClick = {},
        onBackClick = {}
    )
}