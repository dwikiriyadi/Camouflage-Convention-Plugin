package com.camouflage.convention.plugins.android

import com.android.build.api.dsl.ApplicationExtension
import com.camouflage.convention.libs
import com.camouflage.convention.plugins.configuration.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            val extension = extensions.getByType<ApplicationExtension>()

            extension.apply {
                defaultConfig {
                    libs.findVersion("targetSdk").ifPresent {
                        targetSdk = it.requiredVersion.toInt()
                    }
                    libs.findVersion("versionCode").ifPresent {
                        versionCode = it.requiredVersion.toInt()
                    }
                }
            }

            configureAndroidCompose(extension)
        }
    }
}