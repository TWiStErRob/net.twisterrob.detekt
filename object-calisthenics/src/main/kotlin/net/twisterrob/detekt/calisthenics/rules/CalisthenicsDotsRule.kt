package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.CodeSmell
import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Rule
import dev.detekt.rules.isPartOf
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.KtThisExpression

/**
 * Object Calisthenics: Rule #5 - One dot per line.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * dog.body.tail.wag()
 * </noncompliant>
 *
 * <compliant>
 * dog.expressHappiness()
 * </compliant>
 */
class CalisthenicsDotsRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #5 - One dot per line.",
) {

	override val ruleId = Id("CalisthenicsDots")

	override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
		super.visitDotQualifiedExpression(expression)
		if (expression.allowsDots()) return
		if (expression.receiverExpression is KtDotQualifiedExpression) {
			report(CodeSmell(Entity.from(expression.dot), description))
		}
	}
}

@Suppress("CalisthenicsWrapPrimitives") // Suggestions welcome.
private fun KtDotQualifiedExpression.allowsDots(): Boolean =
	this.receiverExpression.isQualifiedThis()
			|| this.isPartOf<KtImportDirective>()
			|| this.isPartOf<KtPackageDirective>()

@Suppress("CalisthenicsWrapPrimitives") // Suggestions welcome.
private fun KtExpression.isQualifiedThis(): Boolean =
	this is KtDotQualifiedExpression && this.receiverExpression is KtThisExpression

private val KtDotQualifiedExpression.dot: PsiElement
	get() = this.operationTokenNode.psi
