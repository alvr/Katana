package modules

import utils.baseAndroidConfig
import utils.configureKotlin

plugins {
    com.android.library
    `kotlin-android`
    `kotlin-kapt`
    id("dagger.hilt.android.plugin")
}

kapt.correctErrorTypes = true

android {
    baseAndroidConfig()
    kotlinOptions.configureKotlin()
}
