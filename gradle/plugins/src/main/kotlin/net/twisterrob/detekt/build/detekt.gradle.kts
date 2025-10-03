package net.twisterrob.detekt.build

import dev.detekt.gradle.Detekt

plugins {
	id("dev.detekt")
}

detekt {
	ignoreFailures = false
	buildUponDefaultConfig = true
	allRules = true
	parallel = true

	tasks.withType<Detekt>().configureEach {
		reports {
			html.required = true // human
			xml.required = true // checkstyle
			md.required = true // markdown
			// https://sarifweb.azurewebsites.net
			sarif.required = true // Github Code Scanning
		}
	}
}
