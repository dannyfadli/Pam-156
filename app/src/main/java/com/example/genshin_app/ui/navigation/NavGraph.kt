package com.example.genshin_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.genshin_app.data.container.AppContainer
import com.example.genshin_app.ui.feature.auth.screen.LoginScreen
import com.example.genshin_app.ui.feature.auth.screen.RegisterScreen
import com.example.genshin_app.ui.feature.auth.viewmodel.AuthViewModel
import com.example.genshin_app.ui.feature.auth.viewmodel.LoginViewModel
import com.example.genshin_app.ui.feature.auth.viewmodel.LogoutViewModel
import com.example.genshin_app.ui.feature.auth.viewmodel.RegisterViewModel
import com.example.genshin_app.ui.feature.build.BuildScreen
import com.example.genshin_app.ui.feature.build.BuildViewModel
import com.example.genshin_app.ui.feature.home.CharacterViewModel
import com.example.genshin_app.ui.feature.home.HomeScreen

@Suppress("ktlint:standard:function-naming")
@Composable
fun NavGraph(
    navController: NavHostController,
    container: AppContainer,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        val authViewModel: AuthViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(innerPadding),
        ) {

            /* ================= AUTH ================= */

            composable(Routes.LOGIN) {
                val loginViewModel: LoginViewModel = viewModel {
                    LoginViewModel(
                        authRepository = container.authRepository,
                        authViewModel = authViewModel,
                    )
                }

                LoginScreen(
                    loginViewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.REGISTER)
                    },
                    onNavigateToFeed = {
                        navController.navigate(Routes.HOME)
                    },
                )
            }

            composable(Routes.REGISTER) {
                val registerViewModel: RegisterViewModel = viewModel {
                    RegisterViewModel(container.authRepository)
                }

                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onRegisterSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.navigate(Routes.LOGIN)
                    },
                )
            }

            /* ================= HOME ================= */

            composable(Routes.HOME) {
                val characterViewModel: CharacterViewModel = viewModel {
                    CharacterViewModel(container.characterRepository)
                }
                val logoutViewModel: LogoutViewModel = viewModel {
                    LogoutViewModel(container.authRepository, authViewModel = authViewModel)
                }

                HomeScreen(
                    navController = navController,
                    viewModel = characterViewModel,
                    logoutViewModel = logoutViewModel,
                    onNavigateLogin = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.HOME) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }

            /* ================= BUILDS ================= */

            composable(
                route = Routes.BUILDS,
                arguments = listOf(
                    navArgument("slug") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val slug =
                    backStackEntry.arguments?.getString("slug")
                        ?: return@composable

                val buildViewModel: BuildViewModel = viewModel {
                    BuildViewModel(container.buildRepository)
                }

                BuildScreen(
                    navController = navController,
                    slug = slug,
                    viewModel = buildViewModel,
                    onNavigateHome = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.BUILDS) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}
