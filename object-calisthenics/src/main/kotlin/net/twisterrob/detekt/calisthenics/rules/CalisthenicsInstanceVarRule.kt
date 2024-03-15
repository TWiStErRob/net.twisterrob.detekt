package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

/**
 * Object Calisthenics: Rule #8 - No classes with more than two instance variables.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsInstanceVarRule(
	config: Config = Config.empty,
) : Rule(config) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/4

	override val issue: Issue =
		Issue(
			id = "CalisthenicsInstanceVar",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #8 - No classes with more than two instance variables.",
			debt = Debt.FIVE_MINS
		)
}
