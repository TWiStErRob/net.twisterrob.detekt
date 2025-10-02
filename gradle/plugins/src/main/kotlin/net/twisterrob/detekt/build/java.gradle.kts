package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("org.gradle.java")
}

java {
	sourceCompatibility = libs.versions.java.source.map(JavaVersion::toVersion).get()
	targetCompatibility = libs.versions.java.target.map(JavaVersion::toVersion).get()
}

tasks.withType<JavaCompile>().configureEach {
	options.release = libs.versions.java.target.map(String::toInt)
}
