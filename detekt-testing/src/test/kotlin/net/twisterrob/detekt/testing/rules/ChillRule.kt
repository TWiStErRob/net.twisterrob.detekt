package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule

internal class ChillRule(
	config: Config = Config.empty,
) : Rule(config) {

	override val issue: Issue = Issue(
		id = "Chill",
		description = "All good, nothing's wrong.",
	)
}
