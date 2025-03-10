package hanz.coding.airticketbooking.presentation.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.airticketbooking.presentation.search.components.ListItemScreen
import hanz.coding.airticketbooking.presentation.splash.StatusBarColor
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
class SearchActivity : ComponentActivity() {

    private var from: String = ""
    private var to: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        from = intent.getStringExtra("from") ?: ""
        to = intent.getStringExtra("to") ?: ""
        println("hanz1 onCreate from $from to $to ")

        setContent {
            KoinAndroidContext {
                val viewModel = koinViewModel<SearchViewModel>()

                LaunchedEffect(Unit) {
                    viewModel.loadFlights(from, to)
                }

                val state by viewModel.state.collectAsStateWithLifecycle()
                StatusBarColor()
                ListItemScreen(
                    state = state,
                    from = from,
                    to = to,
                    onBackClick = { finish() }
                )
            }
        }
    }
}