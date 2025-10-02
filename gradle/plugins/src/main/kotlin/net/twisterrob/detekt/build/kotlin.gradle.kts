package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm")
}

kotlin {
	jvmToolchain(libs.versions.java.toolchain.map(String::toInt).get())
	compilerOptions {
		allWarningsAsErrors = true
		verbose = true
	}
}
