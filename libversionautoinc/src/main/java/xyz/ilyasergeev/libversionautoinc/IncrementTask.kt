package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementTask : DefaultTask() {
    internal lateinit var store: AutoIncrementStore
    internal var startBuildNumber : Int = 0
    internal var buildNumberStep : Int = 0

    @TaskAction
    internal fun incrementBuild() {
        val lastBuildVersion = store.buildNumber ?: startBuildNumber.also {
            store.buildNumber = it
        }

        val incrementedBuildVersion = lastBuildVersion + buildNumberStep
        store.buildNumber = incrementedBuildVersion
        System.out.printf("new version is $incrementedBuildVersion")
    }
}