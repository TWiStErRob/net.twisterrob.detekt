package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

/**
 * Object Calisthenics: Rule #3 - Wrap all primitives and Strings.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsWrapPrimitivesRule(
	config: Config = Config.empty
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsWrapPrimitives",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
			debt = Debt.FIVE_MINS
		)
}
