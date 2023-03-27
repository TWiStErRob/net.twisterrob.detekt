package net.twisterrob.detekt.testing

import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.IncorrectTreeStructureException
import org.jetbrains.kotlin.psi.KtCallExpression
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @see PsiTestingExtension
 */
class PsiTestingExtensionTest {

	private val validCode = """
		fun main() {
			println("Hello, world!")
		}
	""".trimIndent()

	@Test fun `without extension`() {
		val ktFile = compileContentForTest(validCode)

		BadRule().visit(ktFile)
	}

	@ExtendWith(PsiTestingExtension::class)
	@Test fun `with extension`() {
		val ktFile = compileContentForTest(validCode)

		assertThrows<IncorrectTreeStructureException> {
			BadRule().visit(ktFile)
		}
		println(ktFile.text)
	}
}

private class BadRule : Rule() {

	override val issue = Issue(
		id = "BadRule",
		description = "Breaks PSI invariants.",
		severity = Severity.Defect,
		debt = Debt.TWENTY_MINS,
	)

	override fun visitCallExpression(expression: KtCallExpression) {
		super.visitCallExpression(expression)
		// This is possible without reflect, but I don't remember how.
		expression.node::class.java.superclass
			.getDeclaredField("myPrevSibling")
			.apply { isAccessible = true }
			.set(expression.node, null)
		// Make sure DebugUtil.checkTreeStructure runs.
		expression.calleeExpression!!.delete()
	}
}
