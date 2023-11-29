import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("org.gradle.java-library")
	id("net.twisterrob.detekt.build.java")
	id("net.twisterrob.detekt.build.kotlin")
	id("net.twisterrob.detekt.build.testing")
	id("net.twisterrob.detekt.build.detekt")
}

dependencies {
	compileOnly(libs.detekt.api)

	implementation(libs.kotlin.stdlib)
	implementation(libs.kotlin.reflect)

	testRuntimeOnly(libs.detekt.api)
	testImplementation(project(":detekt-testing"))

	detektPlugins(libs.detekt.rules.ruleAuthors)
}

@Suppress("UnstableApiUsage")
testing.suites.withType<JvmTestSuite>().configureEach {
	targets.configureEach {
		testTask.configure {
			systemProperty("compile-snippet-tests", project.property("net.twisterrob.detekt.build.compile-test-snippets")!!)
		}
	}
}
