package dev.alvr.katana.features.home.domain.di

import io.kotest.core.spec.style.FreeSpec
import io.mockk.mockkClass
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.mock.MockProvider
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
internal class FeaturesHomeDomainModuleTest : FreeSpec({
    beforeSpec {
        MockProvider.register { clazz -> mockkClass(clazz) }
    }

    "verify featuresHomeDomainModule" - {
        featuresHomeDomainModule.verify()
    }
})
