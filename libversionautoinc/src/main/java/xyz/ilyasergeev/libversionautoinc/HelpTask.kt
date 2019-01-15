package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by i-sergeev on 11/01/2019.
 */
open class HelpTask : DefaultTask() {

    @TaskAction
    internal fun printHelpMessage() {

        System.out.println("Intalled plugins")
        project.plugins.forEach { plugin ->
            System.out.println("$plugin")
        }
        System.out.println("has VersionIncremention plugin - ${project.plugins.hasPlugin(IncrementPlugin::class.java)}")
        val incExtention = project.extensions.getByType(AppExtension::class.java)

        val extension =
            project.extensions.getByType(IncrementPluginExtension::class.java)

        incExtention.applicationVariants.all { variant ->
            System.out.println("build tasks for variant ${variant.name}")
            val increment = extension.increments
                .find { it.name == variant.name }
            if (increment != null) {
                System.out.println("incrementVersionOn${variant.name.capitalize()}")
            } else {
                System.out.println("no increment")
            }
        }
    }
}