package com.camouflage.convention.plugins.kotlin

import com.camouflage.convention.koinLibs

import com.android.build.gradle.LibraryExtension
import com.camouflage.convention.libs
import com.camouflage.convention.plugins.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

class MultiplatformCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                libs.findVersion("targetSdk").ifPresent {
                    defaultConfig.targetSdk = it.requiredVersion.toInt()
                }
                testOptions.animationsDisabled = true
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()

                iosX64()
                iosArm64()
                iosSimulatorArm64()
                applyDefaultHierarchyTemplate()

                sourceSets.commonMain {
                    dependencies {
                        koinLibs.findLibrary("koin.core").ifPresent {
                            implementation(it)
                        }
                    }
                }

                (this as ExtensionAware).extensions.configure<CocoapodsExtension> {
                    configureKotlinCocoapods(this)
                }
            }
        }
    }
}

internal fun Project.configureKotlinCocoapods(
    extension: CocoapodsExtension
) = extension.apply {
    val moduleName = this@configureKotlinCocoapods.path.split(":").drop(1).joinToString("-")
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "16.0"
    name = moduleName
    framework {
        baseName = moduleName
        embedBitcode(BitcodeEmbeddingMode.BITCODE)
    }
}