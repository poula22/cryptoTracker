package com.plcoding.cryptotracker

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>, isDataLayer: Boolean) {
    commonExtension.apply {
        val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation",libs.findLibrary("androidx.core.ktx").get())
            add("implementation",libs.findLibrary("androidx.appcompat").get())
            add("implementation",libs.findLibrary("material").get())
            if (isDataLayer) {
                add("implementation",libs.findBundle("koin").get())
                add("implementation",libs.findBundle("ktor").get())
            }
            add("testImplementation",libs.findLibrary("junit").get())
            add("androidTestImplementation",libs.findLibrary("androidx.junit").get())
        }
    }
}

internal fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *,*,*>) {
    commonExtension.apply {
        //todo try this LibrariesForLibs
        val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.apply {
            apply(libs.findPlugin("compose.compiler").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        }

        buildFeatures.apply {
            compose = true
        }

        dependencies {
            add("implementation",platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation",libs.findBundle("compose").get())

            add("implementation",libs.findLibrary("androidx.core.ktx").get())
            add("implementation",libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
            add("debugImplementation",libs.findBundle("compose.debug").get())
            add("coreLibraryDesugaring",libs.findLibrary("desugar.jdk.libs").get())
            add("implementation",libs.findBundle("koin").get())

            add("testImplementation",libs.findLibrary("junit").get())
            add("androidTestImplementation",libs.findLibrary("androidx.junit").get())
            add("androidTestImplementation",libs.findLibrary("androidx.espresso.core").get())
            add("androidTestImplementation",libs.findLibrary("androidx.compose.bom").get())
            add("androidTestImplementation",libs.findLibrary("junit").get())
        }

    }
}