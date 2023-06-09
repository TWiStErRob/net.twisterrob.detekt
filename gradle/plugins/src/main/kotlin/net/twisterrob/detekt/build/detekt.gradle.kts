package net.twisterrob.detekt.build

import io.gitlab.arturbosch.detekt.Detekt
import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("io.gitlab.arturbosch.detekt")
}

detekt {
	ignoreFailures = false
	allRules = true
	basePath = rootProject.projectDir.absolutePath

	parallel = true

	tasks.withType<Detekt>().configureEach {
		// Target version of the generated JVM bytecode. It is used for type resolution.
		jvmTarget = libs.versions.java.target.get()
		reports {
			html.required.set(true) // human
			xml.required.set(true) // checkstyle
			txt.required.set(true) // console
			// https://sarifweb.azurewebsites.net
			sarif.required.set(true) // Github Code Scanning
		}
	}
}
