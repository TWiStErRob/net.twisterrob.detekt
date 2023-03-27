package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm")
}

kotlin {
	jvmToolchain(libs.versions.java.toolchain.get().toInt())
}

tasks.withType<KotlinCompile>().configureEach {
	compilerOptions {
		allWarningsAsErrors.set(true)
		verbose.set(true)
		freeCompilerArgs.add("-opt-in=kotlin.ExperimentalStdlibApi")
	}
}
