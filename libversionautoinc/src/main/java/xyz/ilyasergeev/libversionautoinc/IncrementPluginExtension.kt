package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementPluginExtension(
        val incrementRules : NamedDomainObjectContainer<IncrementRule> ,
        private val store : AutoIncrementStore
)
{
    val startBuildNumber : Int get() = store.buildNumber ?: 0

    val buildNumberStep : Int get() = 0

    fun incrementRules(action : Action<in NamedDomainObjectContainer<IncrementRule>>)
    {
        action.execute(incrementRules)
    }
}