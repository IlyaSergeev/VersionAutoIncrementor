package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.Property

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementPluginExtension(
        val increments : NamedDomainObjectContainer<Increment> ,
        private val store : AutoIncrementStore
)
{
    val versionCode : Int get() = store.versionCode

    val versionName : String get() = store.versionName

    fun increments(action : Action<in NamedDomainObjectContainer<Increment>>)
    {
        action.execute(increments)
    }
}