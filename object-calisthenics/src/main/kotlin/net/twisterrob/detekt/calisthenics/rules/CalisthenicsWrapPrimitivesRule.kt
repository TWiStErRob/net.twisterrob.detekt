package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.RuleName
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.containingClass
import org.jetbrains.kotlin.psi.psiUtil.isPrivate
import org.jetbrains.kotlin.psi.psiUtil.isPropertyParameter

/**
 * Object Calisthenics: Rule #3 - Wrap all primitives and Strings.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * fun foo(a: Int, b: String)
 * val p: Double
 * var q: Float
 * fun bar(): Boolean
 * </noncompliant>
 *
 * <compliant>
 * fun foo(h: Hour, m: Minute)
 * val p: Pressure
 * val q: Quantity
 * fun bar(): IsAlive
 * </compliant>
 */
class CalisthenicsWrapPrimitivesRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
) {

	override val ruleName = RuleName("CalisthenicsWrapPrimitives")

	override fun visitParameter(parameter: KtParameter) {
		super.visitParameter(parameter)
		if (!parameter.isPrimitiveWrapper()) {
			validate(parameter)
		}
	}

	override fun visitProperty(property: KtProperty) {
		super.visitProperty(property)
		validate(property)
	}

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		if (!function.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
			validate(function)
		}
	}

	private fun validate(declaration: KtCallableDeclaration) {
		if (declaration.typeName in typesNeedWrapping) {
			report(Finding(Entity.atName(declaration), description))
		}
	}

	private companion object {

		private val primitivesNames = listOf(
			"Boolean",
			"Char",
			"Byte",
			"Short",
			"Int",
			"Long",
			"Float",
			"Double",
			"String",
			"Number",
		)

		private val extraTypes = listOf(
			"java.lang.String",
			"java.lang.Number",
		)

		private val qualifiedPrimitiveNames = primitivesNames.map { type -> "kotlin.${type}" }

		private val nullablePrimitiveNames = (primitivesNames + qualifiedPrimitiveNames + extraTypes)
			.flatMap { listOf(it, "$it?") }

		private val typesNeedWrapping = nullablePrimitiveNames.map(Name::identifier)
	}
}

private val KtCallableDeclaration.typeName: Name?
	get() = typeReference?.text?.let(Name::identifier)

@Suppress("CalisthenicsWrapPrimitives") // Suggestions welcome.
private fun KtParameter.isPrimitiveWrapper(): Boolean =
	this.isPropertyParameter() && (this.isInValueClass() || this.isPrivate())

@Suppress("CalisthenicsWrapPrimitives") // Suggestions welcome.
private fun KtDeclaration.isInValueClass(): Boolean =
	this.containingClass()?.isValueClass() == true

@Suppress("CalisthenicsWrapPrimitives") // Suggestions welcome.
private fun KtClassOrObject.isValueClass(): Boolean =
	this.hasModifier(KtTokens.VALUE_KEYWORD)
