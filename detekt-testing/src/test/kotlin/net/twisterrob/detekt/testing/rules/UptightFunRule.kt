package net.twisterrob.detekt.testing.rules

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtNamedFunction

internal class UptightFunRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Everything is wrong.",
) {

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		report(Finding(Entity.from(function), MESSAGE))
	}

	companion object {

		const val MESSAGE = "Bad code!"
	}
}
