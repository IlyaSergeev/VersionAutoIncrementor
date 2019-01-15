package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.reflect.full.declaredFunctions

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementTask : DefaultTask()
{
    internal lateinit var store : AutoIncrementStore
    internal lateinit var variant : ApplicationVariant
    internal var startBuildNumber : Int = 0
    internal var buildNumberStep : Int = 0
    internal lateinit var appExtension : AppExtension

    private val willAssemble
        get() = project.gradle.taskGraph.allTasks
                .map { it.name.split(":").last() }
                .contains("assemble${variant.name.capitalize()}")

    private val singleIncrementTask
        get() = project.gradle.taskGraph.allTasks
                .lastOrNull()?.name == name

    @TaskAction
    internal fun incrementBuild()
    {
        val lastBuildVersion = store.buildNumber ?: startBuildNumber.also {
            store.buildNumber = it
        }

        val incrementedBuildVersion = lastBuildVersion + buildNumberStep
        store.buildNumber = incrementedBuildVersion
        System.out.printf("new version is $incrementedBuildVersion")

        appExtension.defaultConfig.versionName = "${appExtension.defaultConfig.versionName}($incrementedBuildVersion)"
    }

    private fun ApplicationVariant.setVersionName(name : String)
    {

        mergedFlavor::class.declaredFunctions
                .find { it.name == "setVersionName" }
                ?.call(mergedFlavor , name)

    }
}