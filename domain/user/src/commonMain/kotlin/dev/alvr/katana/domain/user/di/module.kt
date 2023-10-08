package dev.alvr.katana.domain.user.di

import dev.alvr.katana.domain.user.usecases.GetUserIdUseCase
import dev.alvr.katana.domain.user.usecases.GetUserInfoUseCase
import dev.alvr.katana.domain.user.usecases.SaveUserIdUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

private val useCasesModule = module {
    factoryOf(::GetUserIdUseCase)
    factoryOf(::SaveUserIdUseCase)
    factoryOf(::GetUserInfoUseCase)
}

val domainUserModule = module {
    includes(useCasesModule)
}
