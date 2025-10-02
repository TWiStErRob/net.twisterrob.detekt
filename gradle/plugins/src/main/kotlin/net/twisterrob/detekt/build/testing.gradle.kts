package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("org.gradle.java")
}

@Suppress("UnstableApiUsage")
testing.suites.withType<JvmTestSuite>().configureEach {
	useJUnitJupiter(libs.versions.junit.jupiter)

	dependencies {
		implementation(project(":test-helpers"))
	}

	targets.configureEach {
		testTask.configure {
			javaLauncher = javaToolchains.launcherFor {
				languageVersion = libs.versions.java.test.map(JavaLanguageVersion::of)
			}
		}
	}
}
