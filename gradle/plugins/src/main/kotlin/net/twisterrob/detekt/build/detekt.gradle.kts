package net.twisterrob.detekt.build

import io.gitlab.arturbosch.detekt.Detekt
import net.twisterrob.detekt.build.dsl.libs

plugins {
	id("io.gitlab.arturbosch.detekt")
}

detekt {
	ignoreFailures = false
	buildUponDefaultConfig = true
	allRules = true
	basePath = rootProject.projectDir

	parallel = true

	tasks.withType<Detekt>().configureEach {
		// Target version of the generated JVM bytecode. It is used for type resolution.
		jvmTarget = libs.versions.java.target.get()
		reports {
			html.required = true // human
			xml.required = true // checkstyle
			txt.required = true // console
			// https://sarifweb.azurewebsites.net
			sarif.required = true // Github Code Scanning
		}
	}
}
