package com.xiaomi.base.ui.kit.components.textinputs.textfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.spacing

/**
 * Xiaomi Outlined Text Field following Material Design 3
 */
@Composable
fun XiaomiOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

/**
 * Xiaomi Filled Text Field following Material Design 3
 */
@Composable
fun XiaomiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

/**
 * Xiaomi Search Field
 * 
 * A specialized text field for search functionality.
 * 
 * @param value Current search query
 * @param onValueChange Callback when search query changes
 * @param modifier Modifier to be applied to the search field
 * @param enabled Whether the search field is enabled
 * @param placeholder Placeholder text for the search field
 * @param onSearch Callback when search is performed
 * @param onClear Callback when search is cleared
 * @param showClearButton Whether to show the clear button
 */
@Composable
fun XiaomiSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String = "Search...",
    onSearch: ((String) -> Unit)? = null,
    onClear: (() -> Unit)? = null,
    showClearButton: Boolean = true
) {
    XiaomiOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (showClearButton && value.isNotEmpty()) {
            {
                IconButton(
                    onClick = {
                        onValueChange("")
                        onClear?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch?.invoke(value)
            }
        )
    )
}

/**
 * Xiaomi Password Field
 * 
 * A specialized text field for password input.
 * 
 * @param value Current password value
 * @param onValueChange Callback when password changes
 * @param modifier Modifier to be applied to the password field
 * @param enabled Whether the password field is enabled
 * @param label Label for the password field
 * @param placeholder Placeholder text
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 * @param showPasswordToggle Whether to show password visibility toggle
 */
@Composable
fun XiaomiPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "Password",
    placeholder: String = "Enter password",
    isError: Boolean = false,
    supportingText: String? = null,
    showPasswordToggle: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    XiaomiOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Password",
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (showPasswordToggle) {
            {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else null,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } }
    )
}

/**
 * Xiaomi Email Field
 * 
 * A specialized text field for email input.
 * 
 * @param value Current email value
 * @param onValueChange Callback when email changes
 * @param modifier Modifier to be applied to the email field
 * @param enabled Whether the email field is enabled
 * @param label Label for the email field
 * @param placeholder Placeholder text
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 */
@Composable
fun XiaomiEmailField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "Email",
    placeholder: String = "Enter email address",
    isError: Boolean = false,
    supportingText: String? = null
) {
    XiaomiOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email",
                modifier = Modifier.size(20.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } }
    )
}

/**
 * Xiaomi Multiline Text Field
 * 
 * A text field that supports multiple lines of text.
 * 
 * @param value Current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier to be applied to the text field
 * @param enabled Whether the text field is enabled
 * @param label Label for the text field
 * @param placeholder Placeholder text
 * @param minLines Minimum number of lines
 * @param maxLines Maximum number of lines
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 * @param maxCharacters Maximum number of characters allowed
 */
@Composable
fun XiaomiMultilineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "Message",
    placeholder: String = "Enter your message",
    minLines: Int = 3,
    maxLines: Int = 6,
    isError: Boolean = false,
    supportingText: String? = null,
    maxCharacters: Int? = null
) {
    Column {
        XiaomiOutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (maxCharacters == null || newValue.length <= maxCharacters) {
                    onValueChange(newValue)
                }
            },
            modifier = modifier,
            enabled = enabled,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            isError = isError,
            supportingText = supportingText?.let { { Text(it) } }
        )
        
        if (maxCharacters != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${value.length}/$maxCharacters",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (value.length > maxCharacters * 0.9) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

/**
 * Xiaomi Icon Text Field
 * 
 * A text field with customizable leading and trailing icons.
 * 
 * @param value Current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier to be applied to the text field
 * @param enabled Whether the text field is enabled
 * @param label Label for the text field
 * @param placeholder Placeholder text
 * @param leadingIcon Leading icon
 * @param trailingIcon Trailing icon
 * @param isError Whether the field is in error state
 * @param supportingText Supporting text below the field
 * @param keyboardType Type of keyboard to show
 */
@Composable
fun XiaomiIconTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    XiaomiOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        label = { Text(label) },
        placeholder = if (placeholder.isNotEmpty()) {
            { Text(placeholder) }
        } else null,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } }
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Text Fields - Light")
@Composable
fun XiaomiTextFieldsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Text Field Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var textValue by remember { mutableStateOf("") }
                var outlinedValue by remember { mutableStateOf("Sample text") }
                var searchValue by remember { mutableStateOf("") }
                var passwordValue by remember { mutableStateOf("") }
                var emailValue by remember { mutableStateOf("") }
                
                // Filled text field
                XiaomiTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text("Filled Text Field") },
                    placeholder = { Text("Enter text") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Outlined text field
                XiaomiOutlinedTextField(
                    value = outlinedValue,
                    onValueChange = { outlinedValue = it },
                    label = { Text("Outlined Text Field") },
                    supportingText = { Text("This is supporting text") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Search field
                XiaomiSearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    placeholder = "Search products...",
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Password field
                XiaomiPasswordField(
                    value = passwordValue,
                    onValueChange = { passwordValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Email field
                XiaomiEmailField(
                    value = emailValue,
                    onValueChange = { emailValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Icon text field
                XiaomiIconTextField(
                    value = "",
                    onValueChange = {},
                    label = "Username",
                    placeholder = "Enter username",
                    leadingIcon = Icons.Filled.Person,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Text Fields - Error States")
@Composable
fun XiaomiTextFieldsErrorPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Error States",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Error text field
                XiaomiOutlinedTextField(
                    value = "invalid@",
                    onValueChange = {},
                    label = { Text("Email") },
                    isError = true,
                    supportingText = { Text("Please enter a valid email address") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Error password field
                XiaomiPasswordField(
                    value = "123",
                    onValueChange = {},
                    isError = true,
                    supportingText = "Password must be at least 8 characters",
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Disabled text field
                XiaomiOutlinedTextField(
                    value = "Disabled field",
                    onValueChange = {},
                    label = { Text("Disabled") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Multiline Text Field")
@Composable
fun XiaomiMultilineTextFieldPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Multiline Text Field",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var messageValue by remember { mutableStateOf("This is a sample message that spans multiple lines to demonstrate the multiline text field functionality.") }
                
                XiaomiMultilineTextField(
                    value = messageValue,
                    onValueChange = { messageValue = it },
                    label = "Message",
                    placeholder = "Enter your message here...",
                    maxCharacters = 200,
                    supportingText = "Describe your issue in detail",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Text Fields - Dark")
@Composable
fun XiaomiTextFieldsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Dark Theme Text Fields",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var searchValue by remember { mutableStateOf("Dark theme search") }
                
                XiaomiSearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    placeholder = "Search in dark mode...",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiEmailField(
                    value = "user@xiaomi.com",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiPasswordField(
                    value = "password123",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}