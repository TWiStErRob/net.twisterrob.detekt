package net.twisterrob.detekt.testing.rules

import dev.detekt.api.Config
import dev.detekt.api.Rule

internal class ChillRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "All good, nothing's wrong.",
)
