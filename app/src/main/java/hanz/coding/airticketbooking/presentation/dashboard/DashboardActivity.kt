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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import hanz.coding.airticketbooking.presentation.dashboard.component.PassengerCounter
import hanz.coding.airticketbooking.presentation.dashboard.component.TopBar
import hanz.coding.airticketbooking.presentation.dashboard.navigation.TopLevelDestination
import hanz.coding.airticketbooking.presentation.dashboard.state.DashboardState
import hanz.coding.airticketbooking.presentation.dashboard.state.DefaultState
import hanz.coding.airticketbooking.presentation.dashboard.viewmodel.DashboardViewModel
import hanz.coding.airticketbooking.presentation.splash.GradientButton
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import hanz.coding.airticketbooking.rememberAppState
import kotlinx.coroutines.launch
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
    val defaultState by dashboardViewModel.defaultState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) {
        dashboardViewModel.loadLocation()
    }
    StatusBarColor()

    DashboardScreen(
        modifier = modifier,
        defaultState = defaultState,
        state = state,
        snackbarHostState = snackbarHostState,
        onConfirmClick = onConfirmClick,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardState,
    defaultState: DefaultState,
    snackbarHostState: SnackbarHostState,
    onConfirmClick: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val locations = state.locations
    var from = ""
    var to = ""
    var classes = ""
    var adultPassenger = 0
    var childPassenger = 0
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEachIndexed { index, item ->
                item(
                    selected = index == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = index
                        //navigate to screen
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = colorResource(R.color.orange)
                        )
                    },
                    label = {},
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = colorResource(R.color.darkPurple),
        ),
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                )
            },
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.darkPurple2))
                    .padding(padding)
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
                            hint = defaultState.origin,
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
                            hint = defaultState.destination,
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
                                modifier = Modifier.weight(1f),
                                passengerDefault = defaultState.adultPassenger
                            ) { adultPassenger = it }

                            Spacer(modifier = Modifier.width(16.dp))

                            PassengerCounter(
                                title = "Child",
                                modifier = Modifier.weight(1f),
                                passengerDefault = defaultState.childPassenger
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
                            hint = defaultState.classes,
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
                                } else {
                                    coroutineScope.launch {
                                        onShowSnackbar("Please Select All Seat To Continue", null)
                                    }
                                }
                            },
                            text = stringResource(R.string.btn_search)
                        )
                    }
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
        snackbarHostState = SnackbarHostState(),
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