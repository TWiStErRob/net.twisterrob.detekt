rootProject.name = "net-twisterrob-detekt-plugins"

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			from(files("../libs.versions.toml"))
		}
	}
	repositories {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		gradlePluginPortal()
	}
}
