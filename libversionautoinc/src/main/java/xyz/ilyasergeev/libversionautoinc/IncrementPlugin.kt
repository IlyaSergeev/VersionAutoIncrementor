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
                store
            )

        val helloExtention =
            project.extensions.create(
                "hello",
                HelloExtention::class.java
            )

        project.afterEvaluate {
            if (project.plugins.hasPlugin(IncrementPlugin::class.java)) {

                project.tasks.create(
                    "helloTask",
                    HelloTask::class.java
                ) {
                    it.message = helloExtention.message
                    it.recipient = "test"

                    project.plugins.forEach { plugin ->
                        System.out.println("$plugin - ${plugin is IncrementPlugin}")
                    }
                    System.out.println("has plugin - ${project.plugins.hasPlugin(IncrementPlugin::class.java)}")
                    val incExtention = project.extensions.getByType(AppExtension::class.java)

                    incExtention.applicationVariants.all { variant ->
                        System.out.println("variant - ${variant.name}")
                        val increment = extension.increments
                            .find { it.name == variant.name }
                        if (increment != null) {
                            System.out.println("incrementVersionOn${variant.name.capitalize()}")
                        } else {
                            System.out.println("no increment")
                        }
                    }
                }

                val incExtention = project.extensions.getByType(AppExtension::class.java)

                incExtention.applicationVariants.all { variant ->
                    val increment = extension.increments
                        .find { it.name == variant.name }
                    if (increment != null) {
                        val task = project.tasks.create(
                            "incrementVersionOn${variant.name.capitalize()}",
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
        }
    }
}