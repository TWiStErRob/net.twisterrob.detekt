package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Severity
import net.twisterrob.detekt.testing.PsiTestingExtension
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsWrapCollectionsRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsWrapCollectionsRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsWrapCollectionsRule().issue) {
				assertThat(id, equalTo("CalisthenicsWrapCollections"))
				assertThat(debt, equalTo(Debt.FIVE_MINS))
				assertThat(severity, equalTo(Severity.Maintainability))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #4 - First class collections."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsWrapCollectionsRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock(Config::class.java)

			val issue = CalisthenicsWrapCollectionsRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {
		// TODO
	}
}
