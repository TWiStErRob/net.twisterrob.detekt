package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule

internal class ChillRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "All good, nothing's wrong.",
)
