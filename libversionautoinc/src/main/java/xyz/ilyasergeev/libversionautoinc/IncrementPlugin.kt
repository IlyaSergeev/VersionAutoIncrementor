package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class IncrementPlugin : Plugin<Project> {
    companion object {
        private const val GROUP_NAME = "Version incrementation"
        private const val BLOCK_INCREMENT_EXTENSION = "versionIncrement"
    }

    override fun apply(project: Project) {
        val store = AutoIncrementStore(project)

        val extension =
            project.extensions.create(
                BLOCK_INCREMENT_EXTENSION,
                IncrementPluginExtension::class.java,
                project.container(IncrementRule::class.java),
                store
            )

        project.afterEvaluate {
            if (project.plugins.hasPlugin(IncrementPlugin::class.java)) {

                project.tasks.register("helpTask", HelpTask::class.java) { helpTask ->
                    helpTask.group = GROUP_NAME
                }

                val appExtension = project.extensions.getByType(AppExtension::class.java)

                appExtension.applicationVariants.all { variant ->
                    val increment = extension.incrementRules
                        .find { increment -> increment.name == variant.name }

                    if (increment != null) {
                        val task = project.tasks.register(
                            "incrementVersionOn${variant.name.capitalize()}",
                            IncrementTask::class.java
                        ) { incrementTask ->

                            incrementTask.group = GROUP_NAME

                            incrementTask.store = store
                            incrementTask.variant = variant
                            incrementTask.incrementRule = increment
                            incrementTask.appExtension = appExtension
                        }

                        variant.preBuildProvider.dependsOn(task)
                    }
                }
            }
        }
    }
}