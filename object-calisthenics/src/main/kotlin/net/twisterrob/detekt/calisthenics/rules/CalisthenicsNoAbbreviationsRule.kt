package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule

/**
 * Object Calisthenics: Rule #6 - Don’t abbreviate.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsNoAbbreviationsRule(
	config: Config = Config.empty,
) : Rule(config) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/5

	override val issue: Issue =
		Issue(
			id = "CalisthenicsNoAbbreviations",
			description = "Object Calisthenics: Rule #6 - Don’t abbreviate.",
		)
}
