package net.twisterrob.detekt.build

import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("org.gradle.java")
}

java {
	targetCompatibility = libs.versions.java.target.map(JavaVersion::toVersion).get()
}

tasks.withType<JavaCompile>().configureEach {
	options.release = libs.versions.java.target.map(String::toInt)
}
