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
				languageVersion = libs.versions.java.toolchainTest.map(JavaLanguageVersion::of)
			}
			if (libs.versions.kotlin.target.get() < "1.5") {
				jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
			} else {
				@Suppress("detekt.ThrowingExceptionsWithoutMessageOrCause")
				logger.warn("Review --add-opens hack for https://youtrack.jetbrains.com/issue/KT-51619.", Throwable())
			}
		}
	}
}
