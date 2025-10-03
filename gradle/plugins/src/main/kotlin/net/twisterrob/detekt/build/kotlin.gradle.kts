package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("org.jetbrains.kotlin.jvm")
}

kotlin {
	compilerOptions {
		allWarningsAsErrors = true
		freeCompilerArgs.add(jvmTarget.map { "-Xjdk-release=${it.target}" })
		jvmTarget = libs.versions.java.target.map(JvmTarget::fromTarget)
	}
}
