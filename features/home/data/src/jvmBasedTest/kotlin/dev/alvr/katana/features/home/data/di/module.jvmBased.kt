package dev.alvr.katana.features.home.data.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dev.alvr.katana.features.home.data.entities.HomePreferences
import okio.Path
import okio.fakefilesystem.FakeFileSystem

internal actual fun corruptionHandler(createFile: Path) = ReplaceFileCorruptionHandler {
    FakeFileSystem().delete(createFile) // Delete to be able to create the file again
    HomePreferences(welcomeCardVisible = false)
}
