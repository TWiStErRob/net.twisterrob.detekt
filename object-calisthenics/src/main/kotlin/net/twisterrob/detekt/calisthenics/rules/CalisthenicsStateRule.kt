package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule

/**
 * Object Calisthenics: Rule #10 - All classes must have state.
 *
 * Not part of the original
 * [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsStateRule(
	config: Config = Config.empty,
) : Rule(config) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/7

	override val issue: Issue =
		Issue(
			id = "CalisthenicsState",
			description = "Object Calisthenics: Rule #10 - All classes must have state.",
		)
}
