package dev.alvr.katana.buildlogic

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Test.isRelease get() = name.contains("""beta|release""".toRegex(RegexOption.IGNORE_CASE))

internal val Project.fullPackageName get() = KatanaConfiguration.PackageName + path.replace(':', '.')
internal fun Project.catalogVersion(alias: String) = libs.findVersion(alias).get().toString()
internal fun Project.catalogLib(alias: String) = libs.findLibrary(alias).get()
internal fun Project.catalogBundle(alias: String) = libs.findBundle(alias).get()

internal fun KotlinDependencyHandler.catalogVersion(alias: String) = project.catalogVersion(alias)
internal fun KotlinDependencyHandler.catalogLib(alias: String) = project.catalogLib(alias)
internal fun KotlinDependencyHandler.catalogBundle(alias: String) = project.catalogBundle(alias)

internal fun KotlinDependencyHandler.implementation(
    dependencyNotation: Provider<*>,
    configure: ExternalModuleDependency.() -> Unit
) {
    implementation(dependencyNotation.get().toString(), configure)
}

internal fun DependencyHandlerScope.implementation(
    provider: Provider<*>,
    dependencyConfiguration: ExternalModuleDependency.() -> Unit = {},
) {
    "implementation"(provider, dependencyConfiguration)
}

internal fun DependencyHandlerScope.detekt(provider: Provider<*>) {
    "detektPlugins"(provider)
}

internal fun BaseExtension.configureAndroid(packageName: String) {
    compileSdkVersion(KatanaConfiguration.CompileSdk)
    buildToolsVersion(KatanaConfiguration.BuildTools)

    buildFeatures.buildConfig = false
    namespace = packageName

    defaultConfig {
        minSdk = KatanaConfiguration.MinSdk
        targetSdk = KatanaConfiguration.TargetSdk
        versionCode = KatanaConfiguration.VersionCode
        versionName = KatanaConfiguration.VersionName

        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = KatanaConfiguration.UseJavaVersion
        targetCompatibility = KatanaConfiguration.UseJavaVersion
    }

    with(sourceSets["main"]) {
        res.srcDirs("$AndroidDir/res", ResourcesDir)
        resources.srcDirs(ResourcesDir)
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            all { test ->
                test.useJUnitPlatform()
                test.enabled = !test.isRelease
            }
        }
    }
}

internal fun ExtensionContainer.commonExtensions() {
    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(KatanaConfiguration.JvmTargetStr))
            vendor.set(JvmVendorSpec.AZUL)
        }
    }

    configure<KotlinProjectExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(KatanaConfiguration.JvmTargetStr))
            vendor.set(JvmVendorSpec.AZUL)
        }
    }
}

internal fun TaskContainer.commonTasks() {
    withType<JavaCompile>().configureEach {
        sourceCompatibility = KatanaConfiguration.JvmTargetStr
        targetCompatibility = KatanaConfiguration.JvmTargetStr
    }
    withType<KotlinCompile>().configureEach {
        compilerOptions.configureKotlin()
    }
}

private fun KotlinJvmCompilerOptions.configureKotlin() {
    jvmTarget.set(KatanaConfiguration.JvmTarget)
    apiVersion.set(KatanaConfiguration.KotlinVersion)
    languageVersion.set(KatanaConfiguration.KotlinVersion)
    freeCompilerArgs.set(
        freeCompilerArgs.get() + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xcontext-receivers",
            "-Xlambdas=indy",
            "-Xexpect-actual-classes",
        ),
    )
}