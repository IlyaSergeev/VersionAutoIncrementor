package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class IncrementTask : DefaultTask() {
    internal lateinit var store: AutoIncrementStore
    internal lateinit var variant: ApplicationVariant
    internal lateinit var incrementRule: IncrementRule
    internal lateinit var appExtension: AppExtension

    private val willAssemble
        get() = project.gradle.taskGraph.allTasks
            .map { it.name.split(":").last() }
            .contains("assemble${variant.name.capitalize()}")

    private val singleIncrementTask
        get() = project.gradle.taskGraph.allTasks
            .lastOrNull()?.name == name

    @TaskAction
    internal fun incrementBuild() {

        if ((willAssemble || singleIncrementTask)) {

            if (incrementRule.applyPrefix) {
                val lastBuildVersion = store.buildNumber ?: incrementRule.startBuildVersion.also {
                    store.buildNumber = it
                }

                val incrementedBuildVersion = lastBuildVersion + incrementRule.buildNumberStep
                store.buildNumber = incrementedBuildVersion

                val newVersionName = "${variant.versionName}($incrementedBuildVersion)"

                variant.outputs.all { output ->
                    if (output is ApkVariantOutput)
                    {
                        output.versionNameOverride = newVersionName
                        System.out.printf(output.outputFileName)
                    }
                }
            }
        }
    }
}