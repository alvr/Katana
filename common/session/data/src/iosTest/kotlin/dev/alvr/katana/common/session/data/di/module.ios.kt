package dev.alvr.katana.common.session.data.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dev.alvr.katana.common.session.data.entities.Session
import dev.alvr.katana.common.session.domain.models.AnilistToken
import okio.FileSystem
import okio.Path
import okio.fakefilesystem.FakeFileSystem

internal actual val fileSystem = FileSystem.SYSTEM

internal actual fun corruptionHandler(createFile: Path) = ReplaceFileCorruptionHandler {
    FakeFileSystem().delete(createFile, mustExist = false) // Delete to be able to create the file again
    Session(anilistToken = AnilistToken("recreated"))
}
