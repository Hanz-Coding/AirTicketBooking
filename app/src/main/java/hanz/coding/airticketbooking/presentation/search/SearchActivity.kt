package hanz.coding.airticketbooking.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.presentation.search.components.ListItemScreen
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchRoot(
    destinationFrom: String,
    destinationTo: String,
    searchViewModel: SearchViewModel = koinViewModel(),
    onBackClick: () -> Unit
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
    ).also {
        println("hanz1 destinationFrom $destinationFrom destinationTo $destinationTo" )
    }
}

@Composable
fun SearchScreen(
    destinationFrom: String,
    destinationTo: String,
    state: SearchState,
    onBackClick: () -> Unit
) {
    ListItemScreen(
        from = destinationFrom,
        to = destinationTo,
        state = state,
        onBackClick = onBackClick
    )
}