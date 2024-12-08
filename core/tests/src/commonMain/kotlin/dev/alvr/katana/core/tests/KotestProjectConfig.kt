package dev.alvr.katana.core.tests

import dev.alvr.katana.core.tests.coroutines.TestKatanaDispatcher
import dev.alvr.katana.core.tests.di.coreTestsModule
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.config.LogLevel
import io.kotest.core.names.DuplicateTestNameMode
import io.kotest.core.spec.IsolationMode
import io.kotest.core.test.AssertionMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

object KotestProjectConfig : AbstractProjectConfig() {
    private const val NUM_THREADS = 1

    override val assertionMode = AssertionMode.Warn
    override val coroutineDebugProbes = true
    override val duplicateTestNameMode = DuplicateTestNameMode.Silent
    override val globalAssertSoftly = true
    override val includeTestScopePrefixes = true
    override val isolationMode = IsolationMode.SingleInstance
    override val logLevel = LogLevel.Warn
    override val parallelism = NUM_THREADS
    override val testNameAppendTags = true
    override val testNameRemoveWhitespace = true

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun beforeProject() {
        Dispatchers.setMain(TestKatanaDispatcher.main)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun afterProject() {
        Dispatchers.resetMain()
    }

    override fun extensions() = listOf(koinExtension(coreTestsModule))
}
