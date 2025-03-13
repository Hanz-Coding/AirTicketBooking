package hanz.coding.airticketbooking.presentation.ticket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.search.tempFlightModel
import hanz.coding.airticketbooking.presentation.seat_select.SeatSelectActivity

@SuppressLint("DefaultLocale")
@Composable
fun TicketDetailContent(
    item: FlightModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .background(
                color = colorResource(R.color.lightPurple),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            val (logo, arrivalTxt, lineImg, fromTxt, fromShortTxt, toTxt, toShortTxt) = createRefs()
            val context = LocalContext.current
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        context.startActivity(
                            Intent(
                                context,
                                SeatSelectActivity::class.java
                            ).apply {
                                putExtra("flight", item)
                            })
                    }
                    .background(
                        color = colorResource(R.color.lightPurple),
                        shape = RoundedCornerShape(15.dp)
                    )) {
                val (logo, timeTxt, airplaneIcon, dashLine, priceTxt, seatIcon, classTxt,
                    fromTxt, fromShortTxt, toTxt, toShortTxt) = createRefs()

                AsyncImage(
                    model = item.airlineLogo,
                    contentDescription = "airline logo",
                    modifier = Modifier
                        .size(200.dp, 50.dp)
                        .constrainAs(logo) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = item.arriveTime,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = colorResource(R.color.darkPurple2),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .constrainAs(timeTxt) {
                            start.linkTo(parent.start)
                            top.linkTo(logo.bottom)
                            end.linkTo(parent.end)
                        }
                )
                Image(
                    painter = painterResource(R.drawable.line_airple_blue),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .constrainAs(airplaneIcon) {
                            top.linkTo(timeTxt.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = item.arriveTime,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = colorResource(R.color.darkPurple2),
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(classTxt) {
                            start.linkTo(seatIcon.end)
                            bottom.linkTo(seatIcon.bottom)
                            top.linkTo(seatIcon.top)
                        }
                )

                Text(
                    text = item.from,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .constrainAs(fromTxt) {
                            start.linkTo(parent.start)
                            top.linkTo(timeTxt.bottom)
                        }
                )
                Text(
                    text = item.fromShort,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .constrainAs(fromShortTxt) {
                            start.linkTo(fromTxt.start)
                            top.linkTo(fromTxt.bottom)
                            end.linkTo(fromTxt.end)
                        }
                )

                Text(
                    text = item.to,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(toTxt) {
                            end.linkTo(parent.end)
                            top.linkTo(timeTxt.bottom)
                        }
                )
                Text(
                    text = item.toShort,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(toShortTxt) {
                            start.linkTo(toTxt.start)
                            top.linkTo(toTxt.bottom)
                            end.linkTo(toTxt.end)
                        }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                Text(text = "From", color = Color.Black)
                Text(text = item.from, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Date", color = Color.Black)
                Text(text = item.date, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.weight(0.5f)) {
                Text(text = "To", color = Color.Black)
                Text(text = item.to, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Time", color = Color.Black)
                Text(text = item.time, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
        Image(
            painter = painterResource(R.drawable.dash_line),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                Text(text = "Class", color = Color.Black)
                Text(text = item.classSeat, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Seats", color = Color.Black)
                Text(text = item.seats, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.weight(0.5f)) {
                Text(text = "Airlines", color = Color.Black)
                Text(text = item.airlineName, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Price", color = Color.Black)
                Text(
                    text = "$${String.format("%.2f", item.price)}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Image(
                painter = painterResource(R.drawable.qrcode),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 8.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.dash_line),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Image(
            painter = painterResource(R.drawable.barcode),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun TicketDetailContentPreview() {
    TicketDetailContent(
        item = tempFlightModel,
    )
}