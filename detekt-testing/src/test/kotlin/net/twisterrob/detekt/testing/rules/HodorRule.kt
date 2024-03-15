package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.config
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.psiUtil.getCallNameExpression

internal class HodorRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Hodor hodor, hodor.",
) {

	private val replacement: String by config("hodor")

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		function.nameIdentifier?.hodor()
	}

	override fun visitCallExpression(expression: KtCallExpression) {
		super.visitCallExpression(expression)
		expression.getCallNameExpression()?.getIdentifier()?.hodor()
	}

	override fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
		super.visitStringTemplateExpression(expression)
		// Intentionally buggy, not wrapped in autoCorrect, but modifies the PSI tree.
		expression.replaceSelf(KtPsiFactory.contextual(expression).createStringTemplate(replacement))
	}

	private fun PsiElement.hodor() {
		report(CodeSmell(Entity.from(this), MESSAGE))
		if (autoCorrect) {
			replaceSelf(KtPsiFactory.contextual(this).createNameIdentifier(replacement))
		}
	}

	companion object {

		const val MESSAGE = "Hodor hodor"
	}
}

private fun PsiElement.replaceSelf(newElement: PsiElement) {
	// Ideally this would be just replace(newElement), however that requires additional setup.
	// https://github.com/detekt/detekt/issues/6666
	this.node.treeParent.replaceChild(this.node, newElement.node)
}
