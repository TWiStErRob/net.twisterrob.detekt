import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
	id("org.gradle.java-library")
	id("net.twisterrob.detekt.build.java")
	id("net.twisterrob.detekt.build.kotlin")
	id("net.twisterrob.detekt.build.testing")
	id("net.twisterrob.detekt.build.detekt")
}

kotlin {
	explicitApi = ExplicitApiMode.Strict
}
