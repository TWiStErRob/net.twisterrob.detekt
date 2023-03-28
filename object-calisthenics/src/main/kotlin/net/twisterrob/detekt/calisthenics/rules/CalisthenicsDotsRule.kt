package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

/**
 * Object Calisthenics: Rule #5 - One dot per line.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsDotsRule(
	config: Config = Config.empty
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsDots",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #5 - One dot per line.",
			debt = Debt.FIVE_MINS
		)
}
