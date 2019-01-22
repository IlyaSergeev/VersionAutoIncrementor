package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementVersionTask : DefaultTask() {
    internal lateinit var store: AutoIncrementStore

    @TaskAction
    internal fun incrementBuild() {
        System.out.println("run IncrementVersionTask")

        val lastBuildVersion = store.buildNumber ?: 0
        val incrementedBuildVersion = lastBuildVersion + 1
        store.buildNumber = incrementedBuildVersion
    }
}