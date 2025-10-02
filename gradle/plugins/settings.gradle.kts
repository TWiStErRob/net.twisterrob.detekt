rootProject.name = "net-twisterrob-detekt-plugins"

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			from(files("../libs.versions.toml"))
		}
	}
	repositories {
		repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
		gradlePluginPortal()
		maven("Sonatype OSS Snapshots") {
			url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
			content {
				includeVersionByRegex("""io\.gitlab\.arturbosch\.detekt""", """.*""", """main-SNAPSHOT""")
			}
		}
	}
}
