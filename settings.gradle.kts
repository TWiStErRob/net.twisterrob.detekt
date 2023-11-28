import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-detekt"

pluginManagement {
	includeBuild("gradle/plugins")
}

plugins {
	id("net.twisterrob.gradle.plugin.settings") version "0.15.1"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
	}
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors")

include(":object-calisthenics")
include(":detekt-testing")

include(":detekt-browser")
include(":test-helpers")
