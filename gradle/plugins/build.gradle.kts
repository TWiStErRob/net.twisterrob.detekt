plugins {
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
}

dependencies {
	api(libs.plugin.kotlin)
	api(libs.plugin.detekt)
	api(libs.plugin.intellij)

	// TODEL https://github.com/gradle/gradle/issues/15383
	implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}
