package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

internal class ChillRule(
	config: Config = Config.empty,
) : Rule(config) {

	override val issue: Issue = Issue(
		id = "Chill",
		description = "All good, nothing's wrong.",
		severity = Severity.Minor,
		debt = Debt.FIVE_MINS,
	)
}
