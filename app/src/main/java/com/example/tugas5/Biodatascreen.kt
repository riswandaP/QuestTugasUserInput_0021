package com.example.tugas5

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen() {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val skills = stringArrayResource(R.array.skill_options)
    val checked = remember { mutableStateListOf(*BooleanArray(skills.size).toTypedArray()) }

    val isValid = firstName.isNotBlank() && lastName.isNotBlank() &&
            email.isNotBlank() && birthDate.isNotBlank() &&
            experience.isNotBlank() && checked.any { it }

    val toastMessage = stringResource(R.string.toast_success, firstName, lastName)

    Scaffold(
        containerColor = colorResource(R.color.screen_bg),
        bottomBar = {
            Surface(
                color = colorResource(R.color.card_bg),
                shadowElevation = dimensionResource(R.dimen.bottom_bar_elevation),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bottom_bar_height))
            ) {}
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header image
            Image(
                painter = painterResource(R.drawable.mobile),
                contentDescription = stringResource(R.string.cd_header_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.header_height))
            )

            // Indicator dots
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = dimensionResource(R.dimen.dots_padding_start),
                        top = dimensionResource(R.dimen.dots_padding_top)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dot_gap))
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.dot_size))
                            .clip(CircleShape)
                            .background(
                                colorResource(
                                    if (index == 0) R.color.dot_active else R.color.dot_inactive
                                )
                            )
                    )
                }
            }

            // Floating card above Scaffold content
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.card_top_offset)),
                shape = RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.card_corner),
                    topEnd = dimensionResource(R.dimen.card_corner)
                ),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_bg)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(R.dimen.card_elevation)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(R.dimen.card_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.title_form),
                        color = colorResource(R.color.title_purple),
                        fontSize = spResource(R.dimen.text_title),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_small)))
                    Text(
                        text = stringResource(R.string.subtitle_form),
                        color = colorResource(R.color.text_secondary),
                        fontSize = spResource(R.dimen.text_subtitle),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_large)))

                    // Nama depan & belakang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium))
                    ) {
                        FormField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            hint = stringResource(R.string.hint_first_name),
                            modifier = Modifier.weight(1f)
                        )
                        FormField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            hint = stringResource(R.string.hint_last_name),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_medium)))

                    // Email
                    FormField(
                        value = email,
                        onValueChange = { email = it },
                        hint = stringResource(R.string.hint_email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_medium)))

                    // Tgl lahir & experience
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium))
                    ) {
                        BirthDateField(
                            value = birthDate,
                            onDateSelected = { birthDate = it },
                            modifier = Modifier.weight(1f)
                        )
                        ExperienceDropdown(
                            selected = experience,
                            onSelected = { experience = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_medium)))

                    // Skills
                    Text(
                        text = stringResource(R.string.label_skills),
                        color = colorResource(R.color.text_primary),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_small)))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        skills.forEachIndexed { index, skill ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checked[index],
                                    onCheckedChange = { checked[index] = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorResource(R.color.checkbox_checked),
                                        uncheckedColor = colorResource(R.color.text_primary)
                                    )
                                )
                                Text(
                                    text = skill,
                                    color = colorResource(R.color.text_primary),
                                    fontSize = spResource(R.dimen.text_skill)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(dimensionResource(R.dimen.gap_large)))

                    // Proceed
                    Button(
                        onClick = {
                            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                        },
                        enabled = isValid,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.button_enabled),
                            contentColor = colorResource(R.color.button_enabled_text),
                            disabledContainerColor = colorResource(R.color.button_disabled),
                            disabledContentColor = colorResource(R.color.button_disabled_text)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.button_height))
                    ) {
                        Text(
                            text = stringResource(R.string.btn_proceed),
                            fontWeight = FontWeight.Bold,
                            fontSize = spResource(R.dimen.text_button)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun spResource(id: Int): TextUnit = dimensionResource(id).value.sp

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = colorResource(R.color.title_purple),
    unfocusedBorderColor = colorResource(R.color.field_border),
    focusedTextColor = colorResource(R.color.text_primary),
    unfocusedTextColor = colorResource(R.color.text_primary),
    focusedPlaceholderColor = colorResource(R.color.text_secondary),
    unfocusedPlaceholderColor = colorResource(R.color.text_secondary)
)

@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint) },
        singleLine = true,
        shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
        colors = fieldColors(),
        modifier = modifier.height(dimensionResource(R.dimen.field_height))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateField(
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val pattern = stringResource(R.string.date_format)

    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        placeholder = { Text(stringResource(R.string.hint_birth_date)) },
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.cd_pick_date),
                    tint = colorResource(R.color.text_primary)
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
        colors = fieldColors(),
        modifier = modifier.height(dimensionResource(R.dimen.field_height))
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let {
                        onDateSelected(SimpleDateFormat(pattern, Locale.getDefault()).format(Date(it)))
                    }
                    showDialog = false
                }) { Text(stringResource(R.string.dialog_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        ) { DatePicker(state = state) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDropdown(
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = stringArrayResource(R.array.experience_options)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(stringResource(R.string.hint_experience)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(R.dimen.field_corner)),
            colors = fieldColors(),
            modifier = Modifier
                .menuAnchor()
                .height(dimensionResource(R.dimen.field_height))
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}