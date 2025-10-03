package net.twisterrob.detekt.testing.internal

import dev.detekt.api.Config
import dev.detekt.api.Rule
import javax.annotation.CheckReturnValue
import kotlin.reflect.KClass

@CheckReturnValue
@Deprecated("Do not use this method, only visible so that inlining works.", level = DeprecationLevel.ERROR)
@Suppress("detekt.UndocumentedPublicFunction") // Only public because of inlining.
public fun <T : Rule> KClass<T>.newInstance(config: Config): T {
	val constructor =
		this.constructors.singleOrNull { it.parameters.size == 1 && it.parameters[0].type.classifier == Config::class }
			?: error("Rule ${this} should have a constructor where Config is the only parameter.")

	return constructor.call(config)
}
