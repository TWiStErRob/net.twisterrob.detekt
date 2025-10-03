package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.Rule
import dev.detekt.api.RuleName

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
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #4 - First class collections.",
) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/8

	override val ruleName = RuleName("CalisthenicsWrapCollections")
}
