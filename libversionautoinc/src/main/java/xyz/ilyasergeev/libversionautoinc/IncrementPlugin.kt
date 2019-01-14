package xyz.ilyasergeev.libversionautoinc

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class IncrementPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val store = AutoIncrementStore(project)

        val extension =
            project.extensions.create(
                "versionIncrement",
                IncrementPluginExtension::class.java,
                project.container(Increment::class.java),
                store)

        if (project.plugins.hasPlugin(IncrementPlugin::class.java))
        {
            project.afterEvaluate {
                val incExtention = project.extensions.getByType(AppExtension::class.java)

                incExtention.applicationVariants.all { variant ->
                    val increment = extension.increments
                            .find { it.variants.contains(variant.name) }
                    if (increment != null)
                    {
                        val task = project.tasks.create(
                                "increment${increment.name.capitalize()}On${variant.name.capitalize()}" ,
                                IncrementTask::class.java
                        ) {

                            it.group = "Auto incrementor"

                            System.out.printf(extension.toString())
                            it.message = extension.versionName
                            it.recipient = "test"

                        }

                        variant.preBuild.dependsOn(task)
                    }
                }
            }

//            project.afterEvaluate {
//                project.tasks.create("versionIncrement", IncrementTask::class.java) { task ->
//
//                    System.out.printf(extension.toString())
//                    task.message = extension.message
//                    task.recipient = "test"
//                }
//            }
        }
    }
}