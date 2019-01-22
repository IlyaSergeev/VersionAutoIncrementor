package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class SetVersionTask : DefaultTask() {
    internal lateinit var variant: ApplicationVariant
    internal lateinit var incrementRule: IncrementRule
    internal lateinit var appExtension: AppExtension
    internal var buildVersion: Int = 0

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

            val filePrefix: String
            if (incrementRule.applyPrefix) {

                val newVersionName = "${variant.versionName}($buildVersion)"

                variant.outputs.all { output ->
                    if (output is ApkVariantOutput) {
                        output.versionNameOverride = newVersionName
                    }
                }
                filePrefix = newVersionName
            } else {
                filePrefix = variant.versionName
            }
            if (incrementRule.changeFileName) {
                variant.outputs.all { output ->
                    if (output is ApkVariantOutput) {
                        if (incrementRule.changeFileName) {
                            output.outputFileName = addPrefixToFileName(output.outputFileName, "-$filePrefix")
                        }
                    }
                }
            }
        }
    }

    private fun addPrefixToFileName(fileName: String, prefix: String): String {
        val extStartIndex = fileName.lastIndexOf(".")
        return if (extStartIndex < fileName.length) {
            fileName.substring(0, extStartIndex) + prefix + fileName.substring(extStartIndex, fileName.length)
        } else {
            fileName + prefix
        }
    }
}