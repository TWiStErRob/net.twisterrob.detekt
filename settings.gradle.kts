import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-detekt"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
	includeBuild("gradle/plugins")
	repositories {
		gradlePluginPortal()
		maven("Sonatype OSS Snapshots") {
			url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
			content {
				includeVersionByRegex("""io\.gitlab\.arturbosch\.detekt""", """.*""", """main-SNAPSHOT""")
			}
		}
	}
}

plugins {
	id("net.twisterrob.gradle.plugin.settings") version "0.16"
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven("Sonatype OSS Snapshots") {
			url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
			content {
				includeVersionByRegex("""io\.gitlab\.arturbosch\.detekt""", """.*""", """main-SNAPSHOT""")
			}
		}
	}
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors")

include(":object-calisthenics")
include(":detekt-testing")

include(":detekt-browser")
include(":test-helpers")
