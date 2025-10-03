package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.Rule
import dev.detekt.api.RuleName

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
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #10 - All classes must have state.",
) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/7

	override val ruleName = RuleName("CalisthenicsState")
}
