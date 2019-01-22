package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementPluginExtension(
    private val store: AutoIncrementStore
)