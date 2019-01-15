package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementPluginExtension(
        val increments : NamedDomainObjectContainer<Increment> ,
        private val store : AutoIncrementStore
)
{
    val startBuildNumber : Int get() = store.buildNumber

    val buildNumberStep : Int get() = 0

    fun increments(action : Action<in NamedDomainObjectContainer<Increment>>)
    {
        action.execute(increments)
    }
}