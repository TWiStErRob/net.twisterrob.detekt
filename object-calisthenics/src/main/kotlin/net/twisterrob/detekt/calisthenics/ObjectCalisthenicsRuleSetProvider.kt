package net.twisterrob.detekt.calisthenics

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsNoElseRule

/**
 * @see `META-INF/services/io.gitlab.arturbosch.detekt.api.RuleSetProvider` in resources
 */
class ObjectCalisthenicsRuleSetProvider : RuleSetProvider {

	override val ruleSetId: String = "object-calisthenics"

	override fun instance(config: Config): RuleSet =
		RuleSet(
			ruleSetId,
			listOf(
				// CalisthenicsIdentRule(config),
				CalisthenicsNoElseRule(config),
				// CalisthenicsWrapPrimitivesRule(config),
				// CalisthenicsWrapCollectionsRule(config),
				// CalisthenicsDotsRule(config),
				// CalisthenicsNoAbbreviationsRule(config),
				// CalisthenicsSmallRule(config),
				// CalisthenicsInstanceVarRule(config),
				// CalisthenicsNoExposeRule(config),
				// CalisthenicsStateRule(config),
			)
		)
}
