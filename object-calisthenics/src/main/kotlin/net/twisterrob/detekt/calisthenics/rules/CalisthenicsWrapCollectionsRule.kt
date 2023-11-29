package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule

/**
 * Object Calisthenics: Rule #4 - First class collections.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsWrapCollectionsRule(
	config: Config = Config.empty,
) : Rule(config) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/8

	override val issue: Issue =
		Issue(
			id = "CalisthenicsWrapCollections",
			description = "Object Calisthenics: Rule #4 - First class collections.",
		)
}
