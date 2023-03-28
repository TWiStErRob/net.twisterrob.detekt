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
 * @see CalisthenicsWrapPrimitivesRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsWrapPrimitivesRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsWrapPrimitivesRule().issue) {
				assertThat(id, equalTo("CalisthenicsWrapPrimitives"))
				assertThat(debt, equalTo(Debt.FIVE_MINS))
				assertThat(severity, equalTo(Severity.Maintainability))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #3 - Wrap all primitives and Strings."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsWrapPrimitivesRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock(Config::class.java)

			val issue = CalisthenicsWrapPrimitivesRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {
		// TODO
	}
}
