package com.camouflage.convention.plugins.configuration

import com.android.build.api.dsl.CommonExtension
import com.camouflage.convention.composeLibs
import com.camouflage.convention.libs
import com.camouflage.convention.testLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val javaConfig =
        extensions.create("javaConfiguration", JavaConfiguration::class.java)

    commonExtension.apply {
        libs.findVersion("compileSdk").ifPresent {
            compileSdk = it.requiredVersion.toInt()
        }

        defaultConfig {
            libs.findVersion("minSdk").ifPresent {
                minSdk = it.requiredVersion.toInt()
            }
        }

        composeOptions {
            composeLibs.findVersion("compose-compiler").ifPresent {
                kotlinCompilerExtensionVersion = it.requiredVersion
            }
        }

        buildFeatures {
            compose = true
        }

        dependencies {
            with(composeLibs) {
                findLibrary("androidx-compose-bom").ifPresent {
                    add("implementation", platform(it))
                    add("androidTestImplementation", platform(it))
                }
                findLibrary("androidx.compose.ui.tooling").ifPresent {
                    add(
                        "debugImplementation",
                        it
                    )
                }
                findBundle("compose.android.bundle.implementation").ifPresent {
                    add(
                        "implementation",
                        it
                    )
                }
            }

            with(testLibs) {
                findLibrary("junit").ifPresent { add("testImplementation", it) }
            }
        }

        compileOptions {
            sourceCompatibility = javaConfig.version
            targetCompatibility = javaConfig.version
        }
        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = javaConfig.version.toString()
            freeCompilerArgs += buildComposeMetricsParameters()
            freeCompilerArgs += stabilityConfiguration()
            freeCompilerArgs += strongSkippingConfiguration()
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val relativePath = projectDir.relativeTo(rootDir)
    val buildDir = layout.buildDirectory.get().asFile
    val enableMetrics = (enableMetricsProvider.orNull == "true")
    if (enableMetrics) {
        val metricsFolder = buildDir.resolve("compose-metrics").resolve(relativePath)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath,
        )
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = buildDir.resolve("compose-reports").resolve(relativePath)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
        )
    }

    return metricParameters.toList()
}

private fun Project.stabilityConfiguration() = listOf(
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${project.rootDir.absolutePath}/compose_compiler_config.conf",
)

private fun Project.strongSkippingConfiguration() = listOf(
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true",
)