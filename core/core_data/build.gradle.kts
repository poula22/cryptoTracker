plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("plcoding.android.library")
}

android {
    namespace = "com.softspire.core_data"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
dependencies {
    implementation(project(":core:core_domain"))
}