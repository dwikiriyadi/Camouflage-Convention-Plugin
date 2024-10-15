package com.camouflage.convention.plugins.kotlin

import com.camouflage.convention.plugins.configuration.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("com.camouflage.android.lint")
            }
            configureKotlinJvm()
        }
    }
}