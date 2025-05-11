package ru.fan_of_stars.closet

import com.fanofstars.data.dataModule
import com.fanofstars.domain.usecases.RegistrationUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fan_of_stars.closet.ui.registration.RegScreenViewModel

val domainModule = module {
    factory { RegistrationUseCase(get())}
}

val appModule = module {
    viewModel {RegScreenViewModel(get())}
}

val koinModules = listOf(dataModule, appModule, domainModule)