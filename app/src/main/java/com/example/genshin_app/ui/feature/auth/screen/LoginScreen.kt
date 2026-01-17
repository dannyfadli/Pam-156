package com.example.genshin_app.ui.feature.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.genshin_app.R
import com.example.genshin_app.ui.feature.auth.viewmodel.LoginViewModel
import com.example.genshin_app.ui.theme.Dimens
import com.example.genshin_app.ui.theme.LocalAppColors

@Suppress("ktlint:standard:function-naming")
@Composable
fun LoginScreen(
    //region Params
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToFeed: () -> Unit,
    //endregion
) {
    //region State & Variable
    var identifier by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(true) }
    var identifierValueChanged by remember { mutableStateOf(false) }
    var passwordValueChanged by remember { mutableStateOf(false) }

    val colors = LocalAppColors.current
    val isLoading = loginViewModel.isLoading
    val errorMessageNotNull = loginViewModel.errorMessage != null
    val identifierBlank = identifier.isBlank()
    val passwordEmpty = password.isEmpty()
    //endregion

    //region Handler
    // Success handler
    LaunchedEffect(loginViewModel.successMessage) {
        loginViewModel.successMessage?.let {
            onLoginSuccess()
            loginViewModel.consumeSuccess()
        }
    }
    //endregion

    Scaffold(
        containerColor = colors.primaryBackground,
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(Dimens.SpacingL),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //region Title
            Text(
                text =
                    when {
                        errorMessageNotNull -> "Log in"

                        !identifierBlank &&
                                !passwordEmpty &&
                                !isLoading
                            -> stringResource(R.string.app_name)

                        else -> "Log in"
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = colors.mainTitle,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingL))

            //region Input fields for username and password
            OutlinedTextField(
                value = identifier,
                onValueChange = {
                    identifier = it.lowercase().replace(" ", "")
                    identifierValueChanged = true
                    loginViewModel.clearError(identifier, password)
                },
                label = { Text("Username atau Email") },
                isError =
                    identifierValueChanged &&
                            (errorMessageNotNull || identifierBlank),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.extraSmall,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        cursorColor = colors.primaryCursor,
                        focusedBorderColor = colors.focusedBorder,
                        focusedLabelColor = colors.focusedLabel,
                        focusedTextColor = colors.focusedText,
                        errorBorderColor = colors.errorBorder,
                        errorCursorColor = colors.errorCursor,
                        errorLabelColor = colors.errorLabel,
                        errorTextColor = colors.errorText,
                        unfocusedBorderColor = colors.unfocusedBorder,
                        unfocusedLabelColor = colors.unfocusedLabel,
                        unfocusedTextColor = colors.unfocusedText,
                    ),
            )
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingS))

            //region Input fields for password
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordValueChanged = true
                    loginViewModel.clearError(identifier, password)
                },
                label = { Text("Password") },
                isError =
                    passwordValueChanged &&
                            (errorMessageNotNull || passwordEmpty),
                visualTransformation =
                    if (!passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector =
                                if (!passwordVisible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                            contentDescription = null,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.extraSmall,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        cursorColor = colors.primaryCursor,
                        focusedBorderColor = colors.focusedBorder,
                        focusedLabelColor = colors.focusedLabel,
                        focusedTextColor = colors.focusedText,
                        focusedTrailingIconColor = colors.focusedTrailingIcon,
                        errorBorderColor = colors.errorBorder,
                        errorCursorColor = colors.errorCursor,
                        errorLabelColor = colors.errorLabel,
                        errorTextColor = colors.errorText,
                        errorTrailingIconColor = colors.errorTrailingIcon,
                        unfocusedBorderColor = colors.unfocusedBorder,
                        unfocusedLabelColor = colors.unfocusedLabel,
                        unfocusedTextColor = colors.unfocusedText,
                        unfocusedTrailingIconColor = colors.unfocusedTrailingIcon,
                    ),
            )
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingL))

            //region Login button
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = {
                        loginViewModel.login(identifier, password)
                    },
                    enabled = !identifierBlank && !passwordEmpty && !isLoading,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 55.dp),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                when {
                                    identifierBlank ||
                                            passwordEmpty -> colors.buttonContainerDisable

                                    errorMessageNotNull -> colors.buttonContainerError

                                    else -> colors.buttonContainerEnable
                                },
                            contentColor = colors.buttonContentEnable,
                            disabledContainerColor =
                                when {
                                    identifierBlank ||
                                            passwordEmpty -> colors.buttonContainerDisable

                                    errorMessageNotNull -> colors.buttonContainerError

                                    else -> colors.buttonContainerEnable
                                },
                            disabledContentColor = colors.buttonContentDisable,
                        ),
                ) {
                    Text(
                        text =
                            when {
                                identifierBlank && passwordEmpty -> "Semua input tidak boleh kosong"
                                identifierBlank -> "Username atau Email tidak boleh kosong"
                                passwordEmpty -> "Password tidak boleh kosong"
                                isLoading -> "Loading..."
                                errorMessageNotNull -> loginViewModel.errorMessage!!
                                else -> "Log in"
                            },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingL))

            //region Register button
            Row {
                Text(
                    text = "Belum punya akun? ",
                    color = colors.primaryText,
                )

                Text(
                    text = "Register",
                    modifier = Modifier.clickable { onNavigateToRegister() },
                    color = colors.anchor,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium,
                )
            }
            //endregion

            //region Login later button
            TextButton(
                onClick = { onNavigateToFeed() },
            ) {
                Text(
                    text = "Login nanti",
                    fontSize = 16.sp,
                    color = colors.anchor,
                    fontWeight = FontWeight.Medium,
                )
            }
            //endregion
        }
    }
}