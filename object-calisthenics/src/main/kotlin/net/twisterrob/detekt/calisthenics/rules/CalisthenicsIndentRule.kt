package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

/**
 * Object Calisthenics: Rule #1 - One level of indentation per method.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsIndentRule(
	config: Config = Config.empty
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsIndent",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #1 - One level of indentation per method.",
			debt = Debt.FIVE_MINS
		)
}
