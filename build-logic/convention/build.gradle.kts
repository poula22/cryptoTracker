plugins {
    `kotlin-dsl`
}

group = "com.softspire.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}





repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}


gradlePlugin {
    plugins {
        register("AndroidAppCommonPlugin") {
            id = "plcoding.android.application.compose"
            implementationClass = "AndroidAppCommonPlugin"
        }

        register("AndroidLibraryCommonPlugin") {
            id = "plcoding.android.library"
            implementationClass = "AndroidLibraryCommonPlugin"
        }
    }
}