package com.camouflage.convention.plugins.android

import com.android.build.api.dsl.ApplicationExtension
import com.camouflage.convention.composeLibs
import com.camouflage.convention.plugins.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.camouflage.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                composeLibs.findVersion("targetSdk").ifPresent {
                    defaultConfig.targetSdk = it.requiredVersion.toInt()
                }
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
            }
        }
    }
}