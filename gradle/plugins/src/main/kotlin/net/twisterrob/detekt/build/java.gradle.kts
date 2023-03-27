package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("org.gradle.java")
}

tasks.withType<JavaCompile>().configureEach {
	sourceCompatibility = libs.versions.java.source.get()
	targetCompatibility = libs.versions.java.target.get()
}
