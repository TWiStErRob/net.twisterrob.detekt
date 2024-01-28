package net.twisterrob.detekt.calisthenics

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.RuleSet
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

		assertThat(ruleSetId, equalTo(RuleSet.Id("object-calisthenics")))
	}

	@Test
	fun `ruleSet's id is the same as the provider's`(@Mock mockConfig: Config) {
		val ruleSet = sut.instance(mockConfig)

		assertThat(ruleSet.id, equalTo(sut.ruleSetId))
	}

	@Test
	fun `all rules have a consistent id`() {
		val ruleSet = sut.instance()
		val rules = ruleSet.rules

		assertAll(rules.map { (id, provider) -> lazyAssertRuleHasId(provider, id) })
	}

	private fun lazyAssertRuleHasId(ruleProvider: (Config) -> Rule, id: Rule.Id): () -> Unit = {
		val rule = ruleProvider(Config.empty)
		assertThat(rule.ruleId, equalTo(id))
	}

	@Test
	fun `all rules get the ruleset config`(@Mock mockConfig: Config) {
		val ruleSet = sut.instance(mockConfig)
		val rules = ruleSet.rules

		assertAll(rules.map { rule -> lazyAssertRuleHasConfig(rule, mockConfig) })
	}

	private fun lazyAssertRuleHasConfig(rule: Rule, mockConfig: Config): () -> Unit = {
		assertThat(rule.config, sameInstance(mockConfig))
	}

	@Test
	fun `has rule 1`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsIndentRule::class)
	}

	@Test
	fun `has rule 2`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsNoElseRule::class)
	}

	@Test
	fun `has rule 3`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsWrapPrimitivesRule::class)
	}

	@Test
	fun `has rule 4`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsWrapCollectionsRule::class)
	}

	@Test
	fun `has rule 5`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsDotsRule::class)
	}

	@Test
	fun `has rule 6`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsNoAbbreviationsRule::class)
	}

	@Test
	fun `has rule 7`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsSmallRule::class)
	}

	@Test
	fun `has rule 8`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsInstanceVarRule::class)
	}

	@Test
	fun `has rule 9`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsNoExposeRule::class)
	}

	@Test
	fun `has rule 10`() {
		assertHasRule(sut.instance(Config.empty), CalisthenicsStateRule::class)
	}
}

private fun <T : Rule> assertHasRule(ruleSet: RuleSet, ruleClass: KClass<T>) {
	assertThat(ruleSet.rules, hasItem<Rule>(instanceOf(ruleClass.java)))
}
