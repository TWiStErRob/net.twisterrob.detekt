package net.twisterrob.detekt.calisthenics

import io.gitlab.arturbosch.detekt.api.BaseRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import net.twisterrob.detekt.calisthenics.rules.CalisthenicsNoElseRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ObjectCalisthenicsRuleSetProviderTest {

	private val sut = ObjectCalisthenicsRuleSetProvider()

	@Test
	fun `ruleSetId is object-calisthenics`() {
		val ruleSetId = sut.ruleSetId

		assertThat(ruleSetId, equalTo("object-calisthenics"))
	}

	@Test
	fun `ruleSet's id is the same as the provider's`(@Mock mockConfig: Config) {
		val ruleSet = sut.instance(mockConfig)

		assertThat(ruleSet.id, equalTo(sut.ruleSetId))
	}

	@Test
	fun `has rule 2`(@Mock mockConfig: Config) {
		val ruleSet = sut.instance(mockConfig)

		val rule: CalisthenicsNoElseRule = ruleSet.assertHasRule()

		assertThat(rule.ruleSetConfig, sameInstance(mockConfig))
	}
}

private inline fun <reified T : BaseRule> RuleSet.assertHasRule(): T {
	assertThat(this.rules, hasItem<BaseRule>(instanceOf(T::class.java)))
	return this.rules.single { it is T } as T
}
