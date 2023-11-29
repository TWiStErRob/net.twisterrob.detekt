package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import net.twisterrob.detekt.testing.PsiTestingExtension
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsNoExposeRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsNoExposeRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsNoExposeRule().issue) {
				assertThat(id, equalTo("CalisthenicsNoExpose"))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #9 - No getters/setters/properties."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsNoExposeRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsNoExposeRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {
		// TODO
	}
}
