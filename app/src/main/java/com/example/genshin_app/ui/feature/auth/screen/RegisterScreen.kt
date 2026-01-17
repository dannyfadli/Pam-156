package com.example.genshin_app.ui.feature.auth.screen

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.genshin_app.R
import com.example.genshin_app.ui.feature.auth.viewmodel.RegisterViewModel
import com.example.genshin_app.ui.theme.Dimens
import com.example.genshin_app.ui.theme.LocalAppColors

@Suppress("ktlint:standard:function-naming")
@Composable
fun RegisterScreen(
    //region Params
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    //endregion
) {
    //region State & Variable
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(true) }
    var usernameValueChanged by remember { mutableStateOf(false) }
    var emailValueChanged by remember { mutableStateOf(false) }
    var passwordValueChanged by remember { mutableStateOf(false) }
    var termsOfServicePopup by remember { mutableStateOf(false) }
    var privacyPolicePopup by remember { mutableStateOf(false) }

    val colors = LocalAppColors.current
    val isLoading = registerViewModel.isLoading
    val errorMessageNotNull = registerViewModel.errorMessage != null
    val emailMatch = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val usernameBlank = username.isBlank()
    val emailBlank = email.isBlank()
    val passwordEmpty = password.isEmpty()
    //endregion

    //region Handler
    // Success handler
    LaunchedEffect(registerViewModel.successMessage) {
        registerViewModel.successMessage?.let {
            onRegisterSuccess()
            registerViewModel.consumeSuccess()
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
                        errorMessageNotNull -> "Register"

                        !usernameBlank &&
                                !emailBlank &&
                                emailMatch &&
                                !passwordEmpty &&
                                !isLoading
                            -> stringResource(R.string.app_name)

                        else -> "Register"
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = colors.mainTitle,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingS))

            //region Input fields for email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it.lowercase().replace(" ", "")
                    emailValueChanged = true
                    registerViewModel.clearError(username, email, password)
                },
                label = { Text("Email") },
                isError =
                    emailValueChanged &&
                            (errorMessageNotNull || !emailMatch || emailBlank),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                supportingText =
                    if (!emailBlank && !emailMatch) {
                        {
                            Text(
                                text = "Format email tidak valid",
                                color = colors.errorSupportingText,
                            )
                        }
                    } else {
                        null
                    },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(Dimens.SpacingS),
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
                    registerViewModel.clearError(username, email, password)
                },
                label = { Text("Password") },
                isError = passwordValueChanged && (errorMessageNotNull || passwordEmpty),
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
                shape = RoundedCornerShape(Dimens.SpacingS),
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

            Spacer(modifier = Modifier.height(Dimens.SpacingS))

            //region Agreement text button
            val agreementText =
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = colors.primaryText,
                        ),
                    ) {
                        append("Dengan mengklik Register, Anda menyetujui ")
                    }

                    pushStringAnnotation("TOS", "")
                    withStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = colors.anchor,
                        ),
                    ) {
                        append("Syarat dan Ketentuan")
                    }
                    pop()

                    withStyle(
                        SpanStyle(
                            color = colors.primaryText,
                        ),
                    ) {
                        append(" dan ")
                    }

                    pushStringAnnotation("PRIVACY", "")
                    withStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = colors.anchor,
                        ),
                    ) {
                        append("Kebijakan Privasi")
                    }
                    pop()

                    withStyle(
                        SpanStyle(
                            color = colors.primaryText,
                        ),
                    ) {
                        append(" kami.")
                    }
                }
            ClickableText(
                text = agreementText,
                style = TextStyle(textAlign = TextAlign.Center),
            ) { offset ->
                val annotation =
                    agreementText
                        .getStringAnnotations(offset, offset)
                        .firstOrNull()

                when (annotation?.tag) {
                    "TOS" -> termsOfServicePopup = true
                    "PRIVACY" -> privacyPolicePopup = true
                }
            }
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingS))

            //region Register button
            Button(
                onClick = {
                    registerViewModel.register(email, password)
                },
                enabled =
                            !emailBlank &&
                            emailMatch &&
                            !passwordEmpty &&
                            !isLoading,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 55.dp),
                shape = RoundedCornerShape(Dimens.SpacingL),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            when {
                                usernameBlank ||
                                        emailBlank ||
                                        passwordEmpty -> colors.buttonContainerDisable

                                errorMessageNotNull ||
                                        !emailMatch -> colors.buttonContainerError

                                else -> colors.buttonContainerEnable
                            },
                        contentColor = colors.buttonContentEnable,
                        disabledContainerColor =
                            when {
                                usernameBlank ||
                                        emailBlank ||
                                        passwordEmpty -> colors.buttonContainerDisable

                                errorMessageNotNull ||
                                        !emailMatch -> colors.buttonContainerError

                                else -> colors.buttonContainerEnable
                            },
                        disabledContentColor = colors.buttonContentDisable,
                    ),
            ) {
                Text(
                    text =
                        when {
                            usernameBlank && emailBlank && passwordEmpty -> "Semua input tidak boleh kosong"
                            emailBlank && passwordEmpty -> "Email dan Password tidak boleh kosong"
                            emailBlank -> "Email tidak boleh kosong"
                            !emailMatch -> "Email tidak valid"
                            passwordEmpty -> "Password tidak boleh kosong"
                            isLoading -> "Loading..."
                            errorMessageNotNull -> registerViewModel.errorMessage!!
                            else -> "Register"
                        },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
            }
            //endregion

            Spacer(modifier = Modifier.height(Dimens.SpacingL))

            //region Navigate to Login button
            Row {
                Text(
                    text = "Sudah punya akun? ",
                    color = colors.primaryText,
                )

                Text(
                    text = "Login",
                    modifier = Modifier.clickable { onNavigateToLogin() },
                    color = colors.anchor,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium,
                )
            }
            //endregion
        }
    }

    //region Terms of Service Popup
    if (termsOfServicePopup) {
        AlertDialog(
            onDismissRequest = { termsOfServicePopup = false },
            confirmButton = {
                TextButton(onClick = {
                    termsOfServicePopup = false
                }) {
                    Text(
                        text = "MENGERTI",
                        color = colors.textButton,
                    )
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                )
            },
            shape = MaterialTheme.shapes.extraSmall,
            title = { Text("Syarat dan Ketentuan") },
            text = {
                @Suppress("ktlint:standard:max-line-length")
                Text(
                    "Dengan mendaftar, Anda setuju untuk tidak mengunggah konten yang mengandung pornografi, pelecehan kebencian, penipuan, atau konten lain yang melanggar norma agama dan hukum yang berlaku.\n" +
                            "Pelanggaran dapat mengakibatkan penghapusan konten atau penangguhan akun.",
                )
            },
            containerColor = colors.primaryBackground,
            iconContentColor = colors.warningIcon,
            titleContentColor = colors.titleContent,
            textContentColor = colors.primaryText,
        )
    }
    //endregion

    //region Privacy Police Popup
    if (privacyPolicePopup) {
        AlertDialog(
            onDismissRequest = { privacyPolicePopup = false },
            confirmButton = {
                TextButton(onClick = {
                    privacyPolicePopup = false
                }) {
                    Text(
                        text = "MENGERTI",
                        color = colors.textButton,
                    )
                }
            },
            shape = MaterialTheme.shapes.extraSmall,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                )
            },
            title = {
                Text("Kebijakan Privasi")
            },
            text = {
                Text(
                    "Aplikasi ini mengumpulkan data seperti email dan username untuk keperluan autentikasi dan layanan.\n" +
                            "Kami tidak menjual atau membagikan data pribadi kepada pihak ketiga.\n" +
                            "Data disimpan dan dapat dihapus atas permintaan pengguna.",
                )
            },
            containerColor = colors.primaryBackground,
            iconContentColor = colors.warningIcon,
            titleContentColor = colors.titleContent,
            textContentColor = colors.primaryText,
        )
    }
    //endregion
}