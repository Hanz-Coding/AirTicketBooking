@file:OptIn(ExperimentalMaterial3Api::class)

package hanz.coding.airticketbooking.presentation.dashboard.component

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import hanz.coding.airticketbooking.R
import hanz.coding.airticketbooking.presentation.simpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerScreen(modifier: Modifier = Modifier) {
    val dateFormat = remember { simpleDateFormat }

    var departureDate by remember { mutableStateOf(dateFormat.format(Calendar.getInstance().time)) }
    var returnDate by remember { mutableStateOf(dateFormat.format(Calendar.getInstance().time)) }

    Row(modifier = Modifier.fillMaxWidth()) {
        DatePickerItem(
            modifier = modifier,
            dateText = departureDate,
            onDateSelected = { selectedDate ->
                departureDate = selectedDate
            },
            dateFormat = dateFormat,
        )
        Spacer(modifier = Modifier.width(16.dp))

        DatePickerItem(
            modifier = modifier,
            dateText = returnDate,
            onDateSelected = { selectedDate ->
                returnDate = selectedDate
            },
            dateFormat = dateFormat,
        )
    }
}

@Preview
@Composable
fun DatePickerScreenPreview(modifier: Modifier = Modifier) {
    Column {
        DatePickerScreen(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DatePickerItem(
    modifier: Modifier = Modifier,
    dateText: String,
    onDateSelected: (String) -> Unit,
    dateFormat: SimpleDateFormat,
) {
    var openDialog by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .height(60.dp)
            .padding(top = 8.dp)
            .background(
                color = colorResource(R.color.lightPurple),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                openDialog = !openDialog
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.calendar_ic),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(24.dp)
        )
        Text(
            text = dateText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
    if (openDialog) {
        DatePickerDialogScreen(
            dateFormat = dateFormat,
            onDateSelected = onDateSelected,
            onDismissDialog = { openDialog = !openDialog }
        )
    }
}

@Preview
@Composable
fun DatePickerItemPreview() {
    DatePickerItem(
        dateText = simpleDateFormat.format(Calendar.getInstance().time),
        onDateSelected = { },
        dateFormat = simpleDateFormat,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerDialogScreen(
    datePickerState: DatePickerState = DatePickerState(Locale.getDefault()),
    dateFormat: SimpleDateFormat,
    onDismissDialog: () -> Unit,
    onDateSelected: (String) -> Unit
) {

    DatePickerDialog(
        onDismissRequest = { onDismissDialog() },
        confirmButton = {
            TextButton(onClick = {
                val selectedDate =
                    datePickerState.selectedDateMillis?.let { dateFormat.format(Date(it)) }
                onDateSelected(selectedDate ?: "")
                onDismissDialog()
            }) {
                Text(text = stringResource(R.string.str_confirm))
            }
        }) {
        DatePicker(datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DatePickerDialogPreview() {
    DatePickerDialogScreen(
        datePickerState = DatePickerState(locale = Locale.getDefault()),
        dateFormat = simpleDateFormat,
        onDateSelected = {},
        onDismissDialog = {}
    )
}