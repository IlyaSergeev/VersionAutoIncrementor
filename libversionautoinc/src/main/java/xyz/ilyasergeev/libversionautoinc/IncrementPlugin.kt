package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class IncrementPlugin : Plugin<Project> {
    companion object {
        private const val GROUP_NAME = "Version incrementation"
        private const val BLOCK_INCREMENT_EXTENTION = "versionIncrement"
    }

    override fun apply(project: Project) {

        val store = AutoIncrementStore(project)

        val extension =
            project.extensions.create(
                BLOCK_INCREMENT_EXTENTION,
                IncrementPluginExtension::class.java,
                project.container(Increment::class.java),
                store
            )

        project.afterEvaluate {
            if (project.plugins.hasPlugin(IncrementPlugin::class.java)) {

                project.tasks.create("helpTask", HelpTask::class.java) { it.group = GROUP_NAME }

                val incExtention = project.extensions.getByType(AppExtension::class.java)

                incExtention.applicationVariants.all { variant ->
                    val increment = extension.increments
                        .find { it.name == variant.name }
                    if (increment != null) {
                        val task = project.tasks.create(
                            "incrementVersionOn${variant.name.capitalize()}",
                            IncrementTask::class.java
                        ) {

                            it.group = GROUP_NAME

                            System.out.printf(extension.toString())
                            it.message = extension.startBuildNumber.toString()
                            it.recipient = "test"

                        }

                        variant.preBuild.dependsOn(task)
                    }
                }
            }
        }
    }
}