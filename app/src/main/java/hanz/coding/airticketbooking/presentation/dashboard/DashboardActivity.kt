package hanz.coding.airticketbooking.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.navigation.TicketNavHost
import hanz.coding.airticketbooking.presentation.dashboard.navigation.TopLevelDestination
import hanz.coding.airticketbooking.rememberAppState
import org.koin.androidx.compose.KoinAndroidContext
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
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
        }
    }
}

