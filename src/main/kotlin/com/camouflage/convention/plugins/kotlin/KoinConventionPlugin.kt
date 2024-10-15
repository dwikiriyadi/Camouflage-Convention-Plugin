package com.camouflage.convention.plugins.kotlin

import com.camouflage.convention.koinLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                koinLibs.findBundle("koin.android.bundle").ifPresent {
                    add("implementation", it)
                }
            }
        }
    }
}