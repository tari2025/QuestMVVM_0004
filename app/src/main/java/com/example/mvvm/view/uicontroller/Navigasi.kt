
package com.example.mvvm.view.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.mvvm.model.JenisK
import com.example.mvvm.viewmodel.SiswaViewModel
import com.example.mvvm.view.FormSiswa
import com.example.mvvm.view.TampilSiswa

enum class Navigasi {
    Formulir,
    Detail
}

@Composable
fun SiswaApp(
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState = viewModel.statusUI.collectAsState()

    Scaffold(
        modifier = modifier
    ) { isiRuang ->

        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulir.name,
            modifier = Modifier.padding(isiRuang)
        ) {

            composable(route = Navigasi.Formulir.name) {
                val konteks = LocalContext.current

                FormSiswa(
                    pilihanJK = JenisK.map { id ->
                        konteks.resources.getString(id)
                    },
                    onSubmitButtonClicked = { data ->
                        viewModel.setSiswa(data)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }

            composable(route = Navigasi.Detail.name) {
                TampilSiswa(
                    statusUiSiswa = uiState.value,
                    onBackButtonClicked = {
                        cancelAndBackToFormulir(navController)
                    }
                )
            }
        }
    }
}

private fun cancelAndBackToFormulir(
    navController: NavHostController
) {
    navController.popBackStack(
        Navigasi.Formulir.name,
        inclusive = false
    )
}
