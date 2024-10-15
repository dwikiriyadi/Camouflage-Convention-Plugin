package com.camouflage.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

fun Project.getPropertiesByFile(path: String): Properties {
    val properties = Properties()
    val keyPropertiesFile = rootProject.file(path)

    if (keyPropertiesFile.isFile) {
        InputStreamReader(FileInputStream(keyPropertiesFile), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.androidLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("androidLibs")

val Project.composeLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("composeLibs")

val Project.hiltLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("hiltLibs")

val Project.koinLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("koinLibs")

val Project.kotlinLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("kotlinLibs")

val Project.ktorLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("ktorLibs")

val Project.pluginsLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("pluginsLibs")

val Project.testLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("testLibs")

val Project.jacocoLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("jacocoLibs")