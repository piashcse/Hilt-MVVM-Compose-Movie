package com.xiaomi.base.ui.kit.components.textinputs.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.xiaomi.base.ui.kit.components.textinputs.textfields.XiaomiOutlinedTextField
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.spacing
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Xiaomi Date Picker
 * 
 * A Material Design 3 date picker component with Xiaomi design tokens.
 * 
 * @param state DatePickerState for managing the picker state
 * @param modifier Modifier to be applied to the date picker
 * @param dateValidator Function to validate selectable dates
 * @param title Optional title for the date picker
 * @param headline Optional headline for the date picker
 * @param showModeToggle Whether to show the mode toggle button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiDatePicker(
    state: DatePickerState,
    modifier: Modifier = Modifier,
    dateValidator: (Long) -> Boolean = { true },
    title: (@Composable () -> Unit)? = null,
    headline: (@Composable () -> Unit)? = null,
    showModeToggle: Boolean = true
) {
    DatePicker(
        state = state,
        modifier = modifier,
        dateValidator = dateValidator,
        title = title,
        headline = headline,
        showModeToggle = showModeToggle
    )
}

/**
 * Xiaomi Date Picker Dialog
 * 
 * A date picker presented in a dialog.
 * 
 * @param onDateSelected Callback when a date is selected
 * @param onDismiss Callback when the dialog is dismissed
 * @param modifier Modifier to be applied to the dialog
 * @param initialDate Initial date to show (defaults to current date)
 * @param dateValidator Function to validate selectable dates
 * @param title Title for the dialog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiDatePickerDialog(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialDate: Long? = null,
    dateValidator: (Long) -> Boolean = { true },
    title: String = "Select Date"
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate ?: System.currentTimeMillis()
    )
    
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                enabled = confirmEnabled
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = modifier
    ) {
        XiaomiDatePicker(
            state = datePickerState,
            dateValidator = dateValidator,
            title = { Text(title) }
        )
    }
}

/**
 * Xiaomi Time Picker
 * 
 * A Material Design 3 time picker component.
 * 
 * @param state TimePickerState for managing the picker state
 * @param modifier Modifier to be applied to the time picker
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiTimePicker(
    state: TimePickerState,
    modifier: Modifier = Modifier
) {
    TimePicker(
        state = state,
        modifier = modifier
    )
}

/**
 * Xiaomi Time Picker Dialog
 * 
 * A time picker presented in a dialog.
 * 
 * @param onTimeSelected Callback when a time is selected
 * @param onDismiss Callback when the dialog is dismissed
 * @param modifier Modifier to be applied to the dialog
 * @param initialHour Initial hour (0-23)
 * @param initialMinute Initial minute (0-59)
 * @param is24Hour Whether to use 24-hour format
 * @param title Title for the dialog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiTimePickerDialog(
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialHour: Int = 12,
    initialMinute: Int = 0,
    is24Hour: Boolean = true,
    title: String = "Select Time"
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour
    )
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                
                XiaomiTimePicker(
                    state = timePickerState
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    TextButton(
                        onClick = {
                            onTimeSelected(timePickerState.hour, timePickerState.minute)
                            onDismiss()
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

/**
 * Xiaomi Date Input Field
 * 
 * A text field that opens a date picker when clicked.
 * 
 * @param selectedDate Currently selected date
 * @param onDateSelected Callback when a date is selected
 * @param modifier Modifier to be applied to the field
 * @param label Label for the input field
 * @param placeholder Placeholder text
 * @param enabled Whether the field is enabled
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 * @param dateFormat Format for displaying the date
 */
@Composable
fun XiaomiDateInputField(
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date",
    placeholder: String = "Select date",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    dateFormat: String = "MMM dd, yyyy"
) {
    var showDatePicker by remember { mutableStateOf(false) }
    
    val dateFormatter = remember { SimpleDateFormat(dateFormat, Locale.getDefault()) }
    val displayText = selectedDate?.let { dateFormatter.format(Date(it)) } ?: ""
    
    XiaomiOutlinedTextField(
        value = displayText,
        onValueChange = { },
        modifier = modifier,
        enabled = enabled,
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = {
            IconButton(
                onClick = { if (enabled) showDatePicker = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Select date",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } }
    )
    
    if (showDatePicker) {
        XiaomiDatePickerDialog(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false },
            initialDate = selectedDate
        )
    }
}

/**
 * Xiaomi Time Input Field
 * 
 * A text field that opens a time picker when clicked.
 * 
 * @param selectedHour Currently selected hour
 * @param selectedMinute Currently selected minute
 * @param onTimeSelected Callback when a time is selected
 * @param modifier Modifier to be applied to the field
 * @param label Label for the input field
 * @param placeholder Placeholder text
 * @param enabled Whether the field is enabled
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 * @param is24Hour Whether to use 24-hour format
 */
@Composable
fun XiaomiTimeInputField(
    selectedHour: Int?,
    selectedMinute: Int?,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Time",
    placeholder: String = "Select time",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    is24Hour: Boolean = true
) {
    var showTimePicker by remember { mutableStateOf(false) }
    
    val displayText = if (selectedHour != null && selectedMinute != null) {
        if (is24Hour) {
            "%02d:%02d".format(selectedHour, selectedMinute)
        } else {
            val hour12 = if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
            val amPm = if (selectedHour < 12) "AM" else "PM"
            "%d:%02d %s".format(hour12, selectedMinute, amPm)
        }
    } else ""
    
    XiaomiOutlinedTextField(
        value = displayText,
        onValueChange = { },
        modifier = modifier,
        enabled = enabled,
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = {
            IconButton(
                onClick = { if (enabled) showTimePicker = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "Select time",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } }
    )
    
    if (showTimePicker) {
        XiaomiTimePickerDialog(
            onTimeSelected = onTimeSelected,
            onDismiss = { showTimePicker = false },
            initialHour = selectedHour ?: 12,
            initialMinute = selectedMinute ?: 0,
            is24Hour = is24Hour
        )
    }
}

/**
 * Xiaomi Date Range Picker
 * 
 * A component for selecting a date range.
 * 
 * @param startDate Start date of the range
 * @param endDate End date of the range
 * @param onDateRangeSelected Callback when a date range is selected
 * @param modifier Modifier to be applied to the component
 * @param startLabel Label for the start date field
 * @param endLabel Label for the end date field
 * @param enabled Whether the fields are enabled
 */
@Composable
fun XiaomiDateRangePicker(
    startDate: Long?,
    endDate: Long?,
    onDateRangeSelected: (startDate: Long?, endDate: Long?) -> Unit,
    modifier: Modifier = Modifier,
    startLabel: String = "Start Date",
    endLabel: String = "End Date",
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        XiaomiDateInputField(
            selectedDate = startDate,
            onDateSelected = { newStartDate ->
                onDateRangeSelected(newStartDate, endDate)
            },
            label = startLabel,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        
        XiaomiDateInputField(
            selectedDate = endDate,
            onDateSelected = { newEndDate ->
                onDateRangeSelected(startDate, newEndDate)
            },
            label = endLabel,
            enabled = enabled,
            isError = startDate != null && endDate != null && endDate < startDate,
            supportingText = if (startDate != null && endDate != null && endDate < startDate) {
                "End date must be after start date"
            } else null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Date Pickers - Light")
@Composable
fun XiaomiDatePickersPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Date & Time Pickers",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var selectedDate by remember { mutableStateOf<Long?>(null) }
                var selectedHour by remember { mutableStateOf<Int?>(null) }
                var selectedMinute by remember { mutableStateOf<Int?>(null) }
                var startDate by remember { mutableStateOf<Long?>(null) }
                var endDate by remember { mutableStateOf<Long?>(null) }
                
                // Date input field
                XiaomiDateInputField(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    label = "Event Date",
                    placeholder = "Choose event date",
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Time input field
                XiaomiTimeInputField(
                    selectedHour = selectedHour,
                    selectedMinute = selectedMinute,
                    onTimeSelected = { hour, minute ->
                        selectedHour = hour
                        selectedMinute = minute
                    },
                    label = "Event Time",
                    placeholder = "Choose event time",
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Date range picker
                Text(
                    "Date Range",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                XiaomiDateRangePicker(
                    startDate = startDate,
                    endDate = endDate,
                    onDateRangeSelected = { start, end ->
                        startDate = start
                        endDate = end
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Show selected values
                if (selectedDate != null || selectedHour != null || startDate != null) {
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                    ) {
                        Text(
                            "Selected Values:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        selectedDate?.let {
                            Text(
                                "Date: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        
                        if (selectedHour != null && selectedMinute != null) {
                            Text(
                                "Time: %02d:%02d".format(selectedHour, selectedMinute),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        
                        if (startDate != null && endDate != null) {
                            val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
                            Text(
                                "Range: ${formatter.format(Date(startDate!!))} - ${formatter.format(Date(endDate!!))}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi Date Picker Component")
@Composable
fun XiaomiDatePickerComponentPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    "Date Picker Component",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = System.currentTimeMillis()
                )
                
                XiaomiDatePicker(
                    state = datePickerState,
                    title = { Text("Select Date") },
                    headline = { Text("Choose your preferred date") }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Date Pickers - Dark")
@Composable
fun XiaomiDatePickersDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Dark Theme Date Pickers",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var appointmentDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }
                var appointmentHour by remember { mutableStateOf<Int?>(14) }
                var appointmentMinute by remember { mutableStateOf<Int?>(30) }
                
                XiaomiDateInputField(
                    selectedDate = appointmentDate,
                    onDateSelected = { appointmentDate = it },
                    label = "Appointment Date",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiTimeInputField(
                    selectedHour = appointmentHour,
                    selectedMinute = appointmentMinute,
                    onTimeSelected = { hour, minute ->
                        appointmentHour = hour
                        appointmentMinute = minute
                    },
                    label = "Appointment Time",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}