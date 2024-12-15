package dev.alvr.katana.core.preferences.datastore

import android.app.Application
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.koin.mp.KoinPlatform

internal actual fun dataStorePath(file: String) =
    KoinPlatform.getKoin().get<Application>().dataStoreFile(file).toOkioPath()

internal actual val fileSystem: FileSystem = FileSystem.SYSTEM

internal actual fun <T> replaceFile(create: (CorruptionException) -> T) = ReplaceFileCorruptionHandler(create)
