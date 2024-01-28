package net.twisterrob.detekt.calisthenics

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsDotsRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsIndentRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsInstanceVarRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsNoAbbreviationsRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsNoElseRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsNoExposeRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsSmallRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsStateRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapCollectionsRule
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRule

/**
 * @see `META-INF/services/io.gitlab.arturbosch.detekt.api.RuleSetProvider` in resources
 */
class ObjectCalisthenicsRuleSetProvider : RuleSetProvider {

	@Suppress("CalisthenicsWrapPrimitives") // External API.
	override val ruleSetId = RuleSet.Id("object-calisthenics")

	@Suppress("CalisthenicsSmall") // Impossible to shorten.
	override fun instance(config: Config): RuleSet =
		RuleSet(
			ruleSetId,
			listOf(
				CalisthenicsIndentRule(config),
				CalisthenicsNoElseRule(config),
				CalisthenicsWrapPrimitivesRule(config),
				CalisthenicsWrapCollectionsRule(config),
				CalisthenicsDotsRule(config),
				CalisthenicsNoAbbreviationsRule(config),
				CalisthenicsSmallRule(config),
				CalisthenicsInstanceVarRule(config),
				CalisthenicsNoExposeRule(config),
				CalisthenicsStateRule(config),
			)
		)
}
