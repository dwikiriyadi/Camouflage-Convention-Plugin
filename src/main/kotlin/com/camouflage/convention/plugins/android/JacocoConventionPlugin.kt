package com.camouflage.convention.plugins.android

import com.camouflage.convention.plugins.configuration.configureJacoco

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("jacoco")

            plugins.withId("com.android.application") {
                val androidExtension = extensions.getByType<BaseAppModuleExtension>()
                androidExtension.buildTypes.configureEach {
                    enableAndroidTestCoverage = true
                    enableUnitTestCoverage = true
                }
                configureJacoco(extensions.getByType<ApplicationAndroidComponentsExtension>())
            }

            plugins.withId("com.android.library") {
                val androidExtension = extensions.getByType<LibraryExtension>()

                androidExtension.buildTypes.configureEach {
                    enableAndroidTestCoverage = true
                    enableUnitTestCoverage = true
                }

                configureJacoco(extensions.getByType<LibraryAndroidComponentsExtension>())
            }
        }
    }
}