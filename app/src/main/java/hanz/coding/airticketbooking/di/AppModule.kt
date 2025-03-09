package hanz.coding.airticketbooking.di

import hanz.coding.airticketbooking.domain.repository.MainRepository
import hanz.coding.airticketbooking.presentation.dashboard.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DashboardViewModel)
    single { MainRepository() }
}