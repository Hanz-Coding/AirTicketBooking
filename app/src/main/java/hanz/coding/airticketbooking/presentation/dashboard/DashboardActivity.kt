package hanz.coding.airticketbooking.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.navigation.TicketNavHost
import hanz.coding.airticketbooking.presentation.dashboard.component.DatePickerScreen
import hanz.coding.airticketbooking.presentation.dashboard.component.DropDownList
import hanz.coding.airticketbooking.presentation.dashboard.component.MyBottomBar
import hanz.coding.airticketbooking.presentation.dashboard.component.PassengerCounter
import hanz.coding.airticketbooking.presentation.dashboard.component.TopBar
import hanz.coding.airticketbooking.presentation.dashboard.state.DashboardState
import hanz.coding.airticketbooking.presentation.dashboard.viewmodel.DashboardViewModel
import hanz.coding.airticketbooking.presentation.splash.GradientButton
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import hanz.coding.airticketbooking.rememberAppState
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@SuppressLint("RestrictedApi")
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState()
            val snackbarHostState = remember { SnackbarHostState() }
            KoinAndroidContext {
                TicketNavHost(
                    appState,
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = Short,
                        ) == ActionPerformed
                    },
                )
            }
        }
    }
}

@Composable
fun DashboardRoot(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    onConfirmClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val state by dashboardViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        dashboardViewModel.loadLocation()
    }
    StatusBarColor()
    DashboardScreen(
        modifier = modifier,
        state = state,
        onConfirmClick = onConfirmClick,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onConfirmClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val locations = state.locations
    var from: String = ""
    var to: String = ""
    var classes: String = ""
    var adultPassenger: Int = 0
    var childPassenger: Int = 0
    Scaffold(
        bottomBar = { MyBottomBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.darkPurple2))
                .padding(paddingValues = paddingValues)
        ) {
            item { TopBar() }
            item {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .background(
                            colorResource(R.color.darkPurple), shape = RoundedCornerShape(20.dp)
                        )
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    val locationNames: List<String> = locations.map { it.Name }

                    //From Section
                    YellowTitle("From")
                    DropDownList(
                        items = locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select origin",
                        showLocationLoading = state.isLoading
                    ) { selectedItem ->
                        from = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //To Section
                    YellowTitle("To")
                    DropDownList(
                        items = locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select Destination",
                        showLocationLoading = state.isLoading
                    ) { selectedItem ->
                        to = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //Passenger Counter
                    YellowTitle("Passenger")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PassengerCounter(
                            title = "Adult",
                            modifier = Modifier.weight(1f)
                        ) { adultPassenger = it }

                        Spacer(modifier = Modifier.width(16.dp))

                        PassengerCounter(
                            title = "Child",
                            modifier = Modifier.weight(1f)
                        ) { childPassenger = it }
                    }

                    //calendar Picker
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        YellowTitle("Departure date", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
                        YellowTitle("Return date", Modifier.weight(1f))
                    }
                    DatePickerScreen(modifier = Modifier.weight(1f))

                    //Class Section
                    Spacer(modifier = Modifier.height(8.dp))
                    YellowTitle("Class")
                    val classItems = listOf(
                        "Business class",
                        "First class",
                        "Economy class"
                    )
                    DropDownList(
                        items = classItems,
                        loadingIcon = painterResource(R.drawable.seat_black_ic),
                        hint = "Select Class",
                        showLocationLoading = state.isLoading
                    ) { selectedItem ->
                        classes = selectedItem
                    }

                    // Search Button
                    Spacer(modifier = Modifier.height(8.dp))
                    GradientButton(
                        onClick = {
                            val enable = from.isNotEmpty() && to.isNotEmpty()
                            if (enable) {
                                onConfirmClick(from, to)
                            }
                        },
                        text = stringResource(R.string.btn_search)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen(
        state = DashboardState(),
        onConfirmClick = { _, _ -> },
        onShowSnackbar = { _, _ -> false })
}

@Composable
fun YellowTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.orange),
        modifier = modifier
    )
}