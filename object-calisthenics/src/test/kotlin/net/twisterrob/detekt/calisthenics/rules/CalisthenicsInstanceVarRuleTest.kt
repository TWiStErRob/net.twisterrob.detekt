package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.RuleName
import net.twisterrob.detekt.testing.PsiTestingExtension
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsInstanceVarRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsInstanceVarRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsInstanceVarRule()) {
				assertThat(ruleName, equalTo(RuleName("CalisthenicsInstanceVar")))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #8 - No classes with more than two instance variables."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsInstanceVarRule().config, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsInstanceVarRule(mockConfig)

			assertThat(issue.config, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {
		// TODO
	}
}
