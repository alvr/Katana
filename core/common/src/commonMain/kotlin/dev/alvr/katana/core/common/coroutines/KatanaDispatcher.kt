package dev.alvr.katana.core.common.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface KatanaDispatcher : CoroutineScope {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = main + SupervisorJob()
}
