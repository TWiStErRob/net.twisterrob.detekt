package net.twisterrob.detekt.testing

import dev.detekt.api.Config
import dev.detekt.api.Rule
import dev.detekt.test.utils.compileContentForTest
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
	}
}

private class BadRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Breaks PSI invariants.",
) {

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
