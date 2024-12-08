package dev.alvr.katana.core.tests

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import io.kotest.core.test.TestScope as KotestTestScope
import kotlinx.coroutines.test.TestScope as CoroutinesTestScope

@OptIn(ExperimentalUuidApi::class)
val String.Companion.random get() = Uuid.random().toString()

@Suppress("UnusedReceiverParameter")
@OptIn(ExperimentalCoroutinesApi::class)
val KotestTestScope.orbitTestScope: CoroutinesTestScope
    get() = TestScope(UnconfinedTestDispatcher())
