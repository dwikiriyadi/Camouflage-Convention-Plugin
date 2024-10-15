package com.camouflage.convention.plugins.android

import com.android.build.gradle.LibraryExtension
import com.camouflage.convention.composeLibs
import com.camouflage.convention.plugins.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.camouflage.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                composeLibs.findVersion("targetSdk").ifPresent {
                    defaultConfig.targetSdk = it.requiredVersion.toInt()
                }
            }
        }
    }
}