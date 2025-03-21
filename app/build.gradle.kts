plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("plcoding.android.application.compose")
}

android {
    namespace = "com.plcoding.cryptotracker"
    kotlinOptions {
        jvmTarget = "17" // Set the desired JVM target here. It must be consistent with Java.
    }
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(project(":core:core_presentation"))
    implementation(project(":crypto:crypto_presentation"))
    implementation(project(":core:core_data"))
    implementation(project(":crypto:crypto_domain"))
    implementation(project(":crypto:crypto_data"))
    implementation(project(":core:core_domain"))
}