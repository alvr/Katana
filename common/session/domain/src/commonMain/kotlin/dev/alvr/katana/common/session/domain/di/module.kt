package dev.alvr.katana.common.session.domain.di

import dev.alvr.katana.common.session.domain.usecases.ClearActiveSessionUseCase
import dev.alvr.katana.common.session.domain.usecases.DeleteAnilistTokenUseCase
import dev.alvr.katana.common.session.domain.usecases.GetAnilistTokenUseCase
import dev.alvr.katana.common.session.domain.usecases.LogOutUseCase
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.common.session.domain.usecases.SaveSessionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val useCasesModule = module {
    factoryOf(::ClearActiveSessionUseCase)
    factoryOf(::DeleteAnilistTokenUseCase)
    factoryOf(::GetAnilistTokenUseCase)
    factoryOf(::LogOutUseCase)
    factoryOf(::SaveSessionUseCase)

    singleOf(::ObserveActiveSessionUseCase)
}

val commonSessionDomainModule = module {
    includes(useCasesModule)
}
