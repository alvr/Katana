package dev.alvr.katana.core.preferences.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

internal actual val fileSystem: FileSystem = FileSystem.SYSTEM

internal actual fun dataStorePath(file: String): Path =
    NSHomeDirectory().toPath().resolve("datastore").resolve(file)

internal actual fun <T> replaceFile(create: (CorruptionException) -> T) = ReplaceFileCorruptionHandler(create)
