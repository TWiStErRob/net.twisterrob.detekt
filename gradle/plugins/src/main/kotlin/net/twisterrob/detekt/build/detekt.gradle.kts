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
			html.required = true
			checkstyle.required = true
			markdown.required = true
			sarif.required = true
		}
	}
}
