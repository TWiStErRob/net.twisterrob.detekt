rootProject.name = "net-twisterrob-detekt-plugins"

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			from(files("../libs.versions.toml"))
		}
	}
}
