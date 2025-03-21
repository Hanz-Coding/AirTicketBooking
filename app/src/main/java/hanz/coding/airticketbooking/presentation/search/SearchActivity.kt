package hanz.coding.airticketbooking.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.presentation.search.components.FlightItem
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchRoot(
    destinationFrom: String,
    destinationTo: String,
    searchViewModel: SearchViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFlightClick: () -> Unit,
) {
    val state by searchViewModel.state.collectAsStateWithLifecycle()
    StatusBarColor()
    LaunchedEffect(Unit) {
        searchViewModel.loadFlights(destinationFrom, destinationTo)
    }
    SearchScreen(
        destinationFrom = destinationFrom,
        destinationTo = destinationTo,
        state = state,
        onBackClick = onBackClick,
        onFlightClick = {
            searchViewModel.updateCurrentFlight(it)
            onFlightClick()
        }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    destinationFrom: String = "",
    destinationTo: String = "",
    onBackClick: () -> Unit,
    onFlightClick: (FlightModel) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkPurple2))
            .padding(top = 36.dp)
            .padding(horizontal = 16.dp)
    ) {
        val (backBtn, headerTitle, worldImg) = createRefs()
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .constrainAs(backBtn)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(
            text = stringResource(R.string.search_result),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(headerTitle) {
                    start.linkTo(backBtn.end, margin = 8.dp)
                    top.linkTo(backBtn.top)
                    bottom.linkTo(backBtn.bottom)
                }
        )
        Image(
            painter = painterResource(R.drawable.world),
            contentDescription = null,
            modifier = Modifier.constrainAs(worldImg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp)
        ) {
            itemsIndexed(state.flights) { index, item ->
                FlightItem(
                    item = item, index = index,
                    onItemClick = onFlightClick
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    SearchScreen(
        state = SearchState(
            flights = listOf(
                tempFlightModel,
                tempFlightModel,
                tempFlightModel,
                tempFlightModel
            )
        ),
        destinationFrom = "",
        destinationTo = "",
        onBackClick = {},
        onFlightClick = {}
    )
}