pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


rootProject.name = "CryptoTracker"
include(":app")
include(":core")
include(":core:core_presentation")
include(":core:core_domain")
include(":core:core_data")
include(":crypto:crypto_presentation")
include(":crypto:crypto_domain")
include(":crypto:crypto_data")
