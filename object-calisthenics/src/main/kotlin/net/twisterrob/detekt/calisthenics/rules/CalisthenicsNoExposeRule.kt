package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule

/**
 * Object Calisthenics: Rule #9 - No getters/setters/properties.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsNoExposeRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #9 - No getters/setters/properties.",
) {

	// TODO https://github.com/TWiStErRob/net.twisterrob.detekt/issues/6

	override val ruleId = Id("CalisthenicsNoExpose")
}
