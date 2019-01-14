package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class IncrementPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val extension =
            project.extensions.create(
                "versionIncrement",
                IncrementPluginExtension::class.java)

        project.afterEvaluate {
            project.tasks.create("versionIncrement", IncrementTask::class.java) { task ->

                System.out.printf(extension.toString())
                task.message = extension.message
                task.recipient = "test"
            }
        }
    }
}