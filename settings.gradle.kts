pluginManagement {
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

rootProject.name = "TzNti"
include(":libraries:designsystem")
include(":libraries:navigation")
include(":server:server-app")
include(":server:domain")
include(":server:data:repository")
include(":server:data:network")
include(":server:features:main")
include(":server:data:scanning")
include(":server:data:memory")
include(":server:data:local")
include(":server:data:archiving")
include(":client:features:config")
include(":client:client-app")
include(":client:domain")
include(":client:features:config")
include(":client:data:network")
include(":client:data:repository")
include(":client:features:scanlist")
