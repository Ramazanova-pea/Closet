package ru.fan_of_stars.closet

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.fanofstars.data.dataModule
import com.fanofstars.domain.usecases.CreateItemUseCase
import com.fanofstars.domain.usecases.CreateLookUseCase
import com.fanofstars.domain.usecases.GetItemsUseCase
import com.fanofstars.domain.usecases.GetLooksUseCase
import com.fanofstars.domain.usecases.GetTagsUseCase
import com.fanofstars.domain.usecases.GetUserByTokenUseCase
import com.fanofstars.domain.usecases.LoginUseCase
import com.fanofstars.domain.usecases.RegistrationUseCase
import com.fanofstars.domain.usecases.UploadImagePathUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fan_of_stars.closet.ui.closet.ClosetScreenViewModel
import ru.fan_of_stars.closet.ui.closet.addItem.AddItemViewModel
import ru.fan_of_stars.closet.ui.closet.addLook.AddLookViewModel
import ru.fan_of_stars.closet.ui.closet.card.ItemViewModel
import ru.fan_of_stars.closet.ui.filter.FilterViewModel
import ru.fan_of_stars.closet.ui.login.LoginViewModel
import ru.fan_of_stars.closet.ui.registration.RegScreenViewModel
import ru.fan_of_stars.closet.ui.user.UserViewModel

val domainModule = module {
    factory { RegistrationUseCase(get())}
    factory { LoginUseCase(get())}
    factory { GetUserByTokenUseCase(get()) }
    factory { GetTagsUseCase(get()) }
    factory { UploadImagePathUseCase(get()) }
    factory { CreateItemUseCase(get()) }
    factory { GetItemsUseCase(get()) }
    factory { CreateLookUseCase(get()) }
    factory { GetLooksUseCase(get())}
}

val appModule = module {
    single { androidContext() as Application }
    viewModel {RegScreenViewModel(get())}
    viewModel { LoginViewModel(get()) }
    viewModel { UserViewModel(getUserByTokenUseCase = get()) }
    viewModel { ClosetScreenViewModel(get(),get()) }
    viewModel { FilterViewModel(get(), get()) }
    viewModel { AddItemViewModel(get(), get(), get(), get()) }
    single { ItemViewModel(get(), get()) }
    viewModel { AddLookViewModel(get()) }

    single <SharedPreferences> {androidContext().getSharedPreferences("auth", Context.MODE_PRIVATE)}
}

val koinModules = listOf(dataModule, appModule, domainModule)