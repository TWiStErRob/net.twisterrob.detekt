import net.twisterrob.detekt.build.dsl.libs
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
	id("org.gradle.java-library")
	id("net.twisterrob.detekt.build.java")
	id("net.twisterrob.detekt.build.kotlin")
	id("net.twisterrob.detekt.build.testing")
	id("net.twisterrob.detekt.build.detekt")
}

dependencies {
	detektPlugins(libs.detekt.rules.libraries)
}

kotlin {
	explicitApi = ExplicitApiMode.Strict
}

detekt {
	config.from(rootProject.file("config/detekt/detekt-library.yml"))
}
