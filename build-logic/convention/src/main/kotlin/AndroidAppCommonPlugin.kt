import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.plcoding.cryptotracker.configureCompose
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidAppCommonPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply(libs.findPlugin("android.application").get().get().pluginId)
                apply(libs.findPlugin("jetbrains.kotlin.android").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                apply {
                    compileSdk = 35

                    defaultConfig {
                        minSdk = 26
                        targetSdk = 35
                        versionCode = 1
                        versionName = "1.0"
                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                        vectorDrawables {
                            useSupportLibrary = true
                        }
                    }

                    compileOptions {
                        isCoreLibraryDesugaringEnabled = true
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }


                    buildFeatures.buildConfig = false
                }

                //todo remove buildConfigField -> data layer
                buildTypes {
                    debug {
                        buildConfigField("String","BASE_URL","\"https://api.coincap.io/v2/\"")
                    }
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        buildConfigField("String","BASE_URL","\"https://api.coincap.io/v2/\"")
                    }
                }

                buildFeatures {
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                //todo replace with ANDROID
                configureCompose(this)
            }
        }
    }

}