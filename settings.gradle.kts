pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral() // Use this instead of jcenter
        gradlePluginPortal()
        maven("https://jitpack.io") // If needed for plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral() // Use this instead of jcenter
        maven("https://jitpack.io")
    }
}

rootProject.name = "ProjectCuoiKy"
include(":app")
