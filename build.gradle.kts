plugins {
    `kotlin-dsl`
    `maven-publish`
    conventionLibs.plugins.gradle.plugin.publish
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(conventionLibs.android.tools.build)
    compileOnly(conventionLibs.kotlin.gradle.plugin)
}

group = "com.camouflage.convention"
version = "0.0.1"

gradlePlugin {
    website = "" // TODO:
    vcsUrl = "" // TODO:

    plugins {
        register("androidApplication") {
            id = "android.application"
            displayName = "Android Application Plugin"
            description = "This Plugin is for Android Application Configuration"
            tags = listOf("android", "application", "configuration", "plugin")
            implementationClass =
                "com.camouflage.convention.plugins.android.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "android.application.compose"
            displayName = "Android Application Compose Plugin"
            description = "This Plugin is for Android Application with Compose Configuration"
            tags = listOf("android", "application", "configuration", "compose", "plugin")
            implementationClass =
                "com.camouflage.convention.plugins.android.AndroidApplicationComposeConventionPlugin"
        }
        register("publication") {
            id = "kotlin.publication"
            displayName = "Maven Publication Configuration Plugin"
            description = "This Plugin is for Publish Configuration"
            tags = listOf("maven", "publish", "configuration", "plugin")
            implementationClass =
                "com.camouflage.convention.plugins.kotlin.PublicationConventionPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}