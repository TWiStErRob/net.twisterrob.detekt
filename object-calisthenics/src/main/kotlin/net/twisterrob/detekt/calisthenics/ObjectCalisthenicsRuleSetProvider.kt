package net.twisterrob.detekt.calisthenics

import dev.detekt.api.RuleSet
import dev.detekt.api.RuleSetProvider
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
 * @see `META-INF/services/dev.detekt.api.RuleSetProvider` in resources
 */
class ObjectCalisthenicsRuleSetProvider : RuleSetProvider {

	@Suppress("CalisthenicsWrapPrimitives") // External API.
	override val ruleSetId = RuleSet.Id("object-calisthenics")

	@Suppress("CalisthenicsSmall") // Impossible to shorten.
	override fun instance(): RuleSet =
		RuleSet(
			ruleSetId,
			listOf(
				::CalisthenicsIndentRule,
				::CalisthenicsNoElseRule,
				::CalisthenicsWrapPrimitivesRule,
				::CalisthenicsWrapCollectionsRule,
				::CalisthenicsDotsRule,
				::CalisthenicsNoAbbreviationsRule,
				::CalisthenicsSmallRule,
				::CalisthenicsInstanceVarRule,
				::CalisthenicsNoExposeRule,
				::CalisthenicsStateRule,
			)
		)
}
