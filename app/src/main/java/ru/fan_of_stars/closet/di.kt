package ru.fan_of_stars.closet

import com.fanofstars.data.dataModule
import com.fanofstars.domain.usecases.LoginUseCase
import com.fanofstars.domain.usecases.RegistrationUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fan_of_stars.closet.ui.login.LoginViewModel
import ru.fan_of_stars.closet.ui.registration.RegScreenViewModel

val domainModule = module {
    factory { RegistrationUseCase(get())}
    factory { LoginUseCase(get())}
}

val appModule = module {
    viewModel {RegScreenViewModel(get())}
    viewModel { LoginViewModel(get()) }
}

val koinModules = listOf(dataModule, appModule, domainModule)