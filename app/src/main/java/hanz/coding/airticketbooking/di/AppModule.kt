package hanz.coding.airticketbooking.di

import hanz.coding.airticketbooking.domain.repository.MainRepository
import hanz.coding.airticketbooking.presentation.dashboard.viewmodel.DashboardViewModel
import hanz.coding.airticketbooking.presentation.search.SearchViewModel
import hanz.coding.airticketbooking.presentation.seat_select.SeatViewModel
import hanz.coding.airticketbooking.presentation.ticket.TicketViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DashboardViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SeatViewModel)
    viewModelOf(::TicketViewModel)
    single { MainRepository() }
}