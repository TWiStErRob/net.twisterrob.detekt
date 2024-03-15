package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtNamedFunction

internal class UptightFunRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Everything is wrong.",
) {

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		report(CodeSmell(Entity.from(function), MESSAGE))
	}

	companion object {

		const val MESSAGE = "Bad code!"
	}
}
