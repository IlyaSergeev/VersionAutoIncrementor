package xyz.ilyasergeev.libversionautoinc

import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val extention =
            project.extensions.create(
                "greeting",
                GreetingPluginExtension::class.java)

        project.afterEvaluate {
            project.tasks.create("hello", Greeting::class.java) { task ->

                System.out.printf(extention.toString())
                task.message = extention.message
                task.recipient = "test"
            }
        }
    }
}