import net.twisterrob.gradle.doNotNagAbout
import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-detekt"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors")

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
	id("net.twisterrob.gradle.plugin.settings") version "0.18"
	id("net.twisterrob.gradle.plugin.nagging") version "0.18"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
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

include(":object-calisthenics")
include(":detekt-testing")

include(":detekt-browser")
include(":test-helpers")

val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle 9.1 vs detekt 1.23.8 https://github.com/detekt/detekt/issues/8452
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"The ReportingExtension.file(String) method has been deprecated. " +
			"This is scheduled to be removed in Gradle 10. " +
			"Please use the getBaseDirectory().file(String) or getBaseDirectory().dir(String) method instead. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#reporting_extension_file",
	"at io.gitlab.arturbosch.detekt.DetektPlugin.apply(DetektPlugin.kt:28)",
)
