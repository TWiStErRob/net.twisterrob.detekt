plugins {
	`kotlin-dsl`
}

dependencies {
	api(libs.plugin.kotlin)
	api(libs.plugin.detekt) {
		exclude(group = "org.gradle.experimental", module = "gradle-public-api")
	}
	api(libs.plugin.intellij)

	// TODEL https://github.com/gradle/gradle/issues/15383
	implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}

kotlin {
	explicitApi()
	compilerOptions {
		allWarningsAsErrors = true
	}
}
