dependencyResolutionManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs {
        create("conventionLibs") {
            from(files("./gradle/convention.versions.toml"))
        }
    }
}

rootProject.name = "camouflage-convention-plugin"