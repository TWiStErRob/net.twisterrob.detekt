package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.psiUtil.getCallNameExpression

internal class HodorRule(config: Config = Config.empty) : Rule(config) {

	override val issue: Issue = Issue(
		id = "Hodor",
		description = "Hodor hodor, hodor.",
		severity = Severity.Minor,
		debt = Debt.FIVE_MINS,
	)

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		hodor(function.nameIdentifier)
	}

	override fun visitCallExpression(expression: KtCallExpression) {
		super.visitCallExpression(expression)
		hodor(expression.getCallNameExpression()?.getIdentifier())
	}

	override fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
		super.visitStringTemplateExpression(expression)
		// Intentionally buggy, not wrapped in autoCorrect, but modifies the PSI tree.
		expression.replace(KtPsiFactory(expression).createStringTemplate("hodor"))
	}

	private fun hodor(identifier: PsiElement?) {
		if (identifier == null) return
		report(CodeSmell(issue, Entity.from(identifier), MESSAGE))
		if (autoCorrect) {
			identifier.replace(KtPsiFactory(identifier).createNameIdentifier("hodor"))
		}
	}

	companion object {

		const val MESSAGE = "Hodor hodor"
	}
}
