package hanz.coding.airticketbooking.presentation.search.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@SuppressLint("DefaultLocale")
@Composable
fun FlightItem(
    item: FlightModel, index: Int,
    onItemClick: (FlightModel) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(item)
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
        Image(
            painter = painterResource(R.drawable.dash_line),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(dashLine) {
                    top.linkTo(airplaneIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "$${String.format("%.2f", item.price)}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            color = colorResource(R.color.orange),
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(priceTxt) {
                    top.linkTo(dashLine.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(R.drawable.seat_black_ic),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(seatIcon) {
                    top.linkTo(dashLine.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
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

@Preview
@Composable
fun FlightItemsPreview(modifier: Modifier = Modifier) {
    FlightItem(
        item = tempFlightModel,
        index = 10,
        onItemClick = {}
    )
}