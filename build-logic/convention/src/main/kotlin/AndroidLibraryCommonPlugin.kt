import com.android.build.api.dsl.LibraryExtension
import com.plcoding.cryptotracker.configureAndroid
import com.plcoding.cryptotracker.configureCompose
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryCommonPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply(libs.findPlugin("android.library").get().get().pluginId)
                apply(libs.findPlugin("jetbrains.kotlin.android").get().get().pluginId)
                apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 35

                defaultConfig {
                    minSdk = 26
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildFeatures.buildConfig = true

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildTypes {
                    debug {
                        buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
                    }
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
                    }
                }

                if (name.endsWith("presentation")) {
                    configureCompose(this)
                } else if (name.endsWith("domain")) {
                    configureAndroid(this, false)
                } else if (name.endsWith("data")) {
                    configureAndroid(this, true)
                }
            }
        }
    }
}