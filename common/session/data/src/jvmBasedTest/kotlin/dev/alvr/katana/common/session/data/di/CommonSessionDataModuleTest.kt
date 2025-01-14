package dev.alvr.katana.common.session.data.di

import io.kotest.core.spec.style.FreeSpec
import io.mockk.mockkClass
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.mock.MockProvider

@OptIn(KoinExperimentalAPI::class)
internal class CommonSessionDataModuleTest : FreeSpec({
    beforeSpec {
        MockProvider.register { clazz -> mockkClass(clazz) }
    }

    "verify commonSessionDomainModule" - {
//        commonSessionDataModule.verify(
//            extraTypes = listOf(
//                KatanaDispatcher::class,
//                SessionRepository::class,
//            ),
//        )
    }
})
