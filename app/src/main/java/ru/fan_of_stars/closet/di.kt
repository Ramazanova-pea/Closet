package ru.fan_of_stars.closet

import com.fanofstars.data.dataModule
import com.fanofstars.domain.usecases.RegistrationUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fan_of_stars.closet.ui.registration.RegScreenViewModel

val domainModule = module {
    factory<RegistrationUseCase> { RegistrationUseCase(get())}
}

val appModule = module {
    single<RegScreenViewModel> {RegScreenViewModel(get())}
}

val koinModules = listOf(dataModule, appModule, domainModule)