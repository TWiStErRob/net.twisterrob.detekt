package net.twisterrob.detekt.calisthenics

import dev.detekt.api.Config
import dev.detekt.api.Rule
import dev.detekt.api.RuleName
import dev.detekt.api.RuleSet
import dev.detekt.api.RuleSetId
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
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.reflect.KClass

@ExtendWith(MockitoExtension::class)
class ObjectCalisthenicsRuleSetProviderTest {

	private val sut = ObjectCalisthenicsRuleSetProvider()

	@Test
	fun `ruleSetId is object-calisthenics`() {
		val ruleSetId = sut.ruleSetId

		assertThat(ruleSetId, equalTo(RuleSetId("object-calisthenics")))
	}

	@Test
	fun `ruleSet's id is the same as the provider's`() {
		val ruleSet = sut.instance()

		assertThat(ruleSet.id, equalTo(sut.ruleSetId))
	}

	@Test
	fun `all rules have a consistent id`() {
		val ruleSet = sut.instance()
		val rules = ruleSet.rules

		assertAll(rules.map { (ruleName, provider) -> lazyAssertRuleHasName(provider, ruleName) })
	}

	private fun lazyAssertRuleHasName(ruleProvider: (Config) -> Rule, ruleName: RuleName): () -> Unit = {
		val rule = ruleProvider(Config.empty)
		assertThat(rule.ruleName, equalTo(ruleName))
	}

	@Test
	fun `all rules get the ruleset config`(@Mock mockConfig: Config) {
		val ruleSet = sut.instance()
		val rules = ruleSet.rules

		assertAll(rules.map { (_, provider) -> lazyAssertRuleHasConfig(provider, mockConfig) })
	}

	private fun lazyAssertRuleHasConfig(ruleProvider: (Config) -> Rule, mockConfig: Config): () -> Unit = {
		val rule = ruleProvider(mockConfig)
		assertThat(rule.config, sameInstance(mockConfig))
	}

	@Test
	fun `has rule 1`() {
		assertHasRule(sut.instance(), CalisthenicsIndentRule::class)
	}

	@Test
	fun `has rule 2`() {
		assertHasRule(sut.instance(), CalisthenicsNoElseRule::class)
	}

	@Test
	fun `has rule 3`() {
		assertHasRule(sut.instance(), CalisthenicsWrapPrimitivesRule::class)
	}

	@Test
	fun `has rule 4`() {
		assertHasRule(sut.instance(), CalisthenicsWrapCollectionsRule::class)
	}

	@Test
	fun `has rule 5`() {
		assertHasRule(sut.instance(), CalisthenicsDotsRule::class)
	}

	@Test
	fun `has rule 6`() {
		assertHasRule(sut.instance(), CalisthenicsNoAbbreviationsRule::class)
	}

	@Test
	fun `has rule 7`() {
		assertHasRule(sut.instance(), CalisthenicsSmallRule::class)
	}

	@Test
	fun `has rule 8`() {
		assertHasRule(sut.instance(), CalisthenicsInstanceVarRule::class)
	}

	@Test
	fun `has rule 9`() {
		assertHasRule(sut.instance(), CalisthenicsNoExposeRule::class)
	}

	@Test
	fun `has rule 10`() {
		assertHasRule(sut.instance(), CalisthenicsStateRule::class)
	}
}

private fun <T : Rule> assertHasRule(ruleSet: RuleSet, ruleClass: KClass<T>) {
	assertThat(ruleSet.createRules(Config.empty), hasItem<Rule>(instanceOf(ruleClass.java)))
}

private val RuleSet.ruleFactories: Collection<(Config) -> Rule>
	get() = this.rules.values

private fun RuleSet.createRules(config: Config): List<Rule> =
	this.ruleFactories.map { it(config) }
