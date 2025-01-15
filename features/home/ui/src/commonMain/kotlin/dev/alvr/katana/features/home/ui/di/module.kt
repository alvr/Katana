package dev.alvr.katana.features.home.ui.di

import dev.alvr.katana.features.home.ui.viewmodel.HomeViewModel
import dev.alvr.katana.features.home.ui.viewmodel.PlatformHomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val viewModelsModule = module {
    viewModelOf(::PlatformHomeViewModel) bind HomeViewModel::class
}

val featuresHomeUiModule = module {
    includes(viewModelsModule)
}
