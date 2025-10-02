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

	targets {
		val minJava = libs.versions.java.target.map(String::toInt).get()
		val maxJava = libs.versions.java.compile.map(String::toInt).get()
		named(name).configure {
			testTask.configure {
				// javaLauncher = <not set, inherited from Gradle, see java-compile.
				description = "Runs the test suite on JDK ${maxJava}"
			}
		}
		listOf(minJava) // Not (minJava..<maxJava) to save time.
			.map(JavaLanguageVersion::of)
			.forEach { javaVersion ->
				register("testJdk${javaVersion.asInt()}") {
					testTask.configure {
						description = "Runs the test suite on JDK ${javaVersion}"
						javaLauncher = javaToolchains.launcherFor {
							languageVersion = javaVersion
						}
					}
				}
			}
	}
}
