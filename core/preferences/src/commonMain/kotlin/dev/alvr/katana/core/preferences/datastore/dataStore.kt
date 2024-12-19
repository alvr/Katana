package dev.alvr.katana.core.preferences.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import dev.alvr.katana.core.common.coroutines.KatanaDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem
import okio.Path
import org.koin.mp.KoinPlatform

internal expect val fileSystem: FileSystem
internal expect fun dataStorePath(file: String): Path
internal expect fun <T> replaceFile(create: (CorruptionException) -> T): ReplaceFileCorruptionHandler<T>

private val ioDispatcher get() = KoinPlatform.getKoin().get<KatanaDispatcher>().io

fun <T> dataStoreFactory(
    name: String,
    serializer: OkioSerializer<T>,
    create: (CorruptionException) -> T,
    migrations: List<DataMigration<T>> = emptyList(),
): DataStore<T> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = fileSystem,
        serializer = serializer,
        producePath = { dataStorePath("$name.pb") },
    ),
    corruptionHandler = replaceFile(create),
    migrations = migrations,
    scope = CoroutineScope(ioDispatcher + SupervisorJob()),
)
