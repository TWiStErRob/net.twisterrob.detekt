package net.twisterrob.detekt.testing.internal

import io.gitlab.arturbosch.detekt.api.BaseRule
import io.gitlab.arturbosch.detekt.api.Config
import javax.annotation.CheckReturnValue
import kotlin.reflect.KClass

@CheckReturnValue
@Deprecated("Do not use this method, only visible so that inlining works.", level = DeprecationLevel.ERROR)
@Suppress("UndocumentedPublicFunction") // Only public because of inlining.
fun <T : BaseRule> KClass<T>.newInstance(config: Config): T {
	val constructor =
		this.constructors.singleOrNull { it.parameters.size == 1 && it.parameters[0].type.classifier == Config::class }
			?: error("Rule ${this} should have a constructor where Config is the only parameter.")

	return constructor.call(config)
}
