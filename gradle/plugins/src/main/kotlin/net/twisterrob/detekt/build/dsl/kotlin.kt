package net.twisterrob.detekt.build.dsl

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal val Project.kotlin: KotlinProjectExtension
    get() = this.extensions.getByName<KotlinProjectExtension>("kotlin")
