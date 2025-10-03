package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.Rule
import dev.detekt.api.RuleName

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
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #8 - No classes with more than two instance variables.",
) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/4

	override val ruleName = RuleName("CalisthenicsInstanceVar")
}
