package net.twisterrob.detekt.calisthenics.rules.internal

@JvmInline
internal value class Count(
	private val count: Int
) : Comparable<Count> {

	override fun compareTo(other: Count): Int =
		this.count.compareTo(other.count)

	override fun toString(): String =
		count.toString()

	companion object {

		@Suppress("CalisthenicsWrapPrimitives")
		fun String.repeat(count: Count): String =
			this.repeat(count.count)
	}
}
