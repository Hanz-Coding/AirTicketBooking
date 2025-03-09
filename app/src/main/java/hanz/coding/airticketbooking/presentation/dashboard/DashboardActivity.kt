package hanz.coding.airticketbooking.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.domain.LocationModel
import hanz.coding.airticketbooking.presentation.dashboard.component.DropDownList
import hanz.coding.airticketbooking.presentation.dashboard.component.MyBottomBar
import hanz.coding.airticketbooking.presentation.dashboard.component.TopBar
import hanz.coding.airticketbooking.presentation.dashboard.state.DashboardState
import hanz.coding.airticketbooking.presentation.dashboard.viewmodel.DashboardViewModel
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.koinViewModel

@SuppressLint("RestrictedApi")
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(true) {
                viewModel.loadLocation()
            }
            MainScreen(state)
        }
    }
}

@Composable
fun MainScreen(state: DashboardState) {
    val locations = state.locations
    var from: String
    var to: String
    var classes: String
    StatusBarColor()
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
                    YellowTitle("From")
                    val locationNames: List<String> = locations.map { it.Name }
                    DropDownList(
                        items = locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select origin",
                        showLocationLoading = state.isLoading
                    ) { selectedItem ->
                        from = selectedItem
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(DashboardState())
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