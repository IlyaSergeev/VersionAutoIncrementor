package com.hensh.autoink

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by i-sergeev on 11/01/2019.
 */
class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.create("hello", Greeting::class.java, {
                task ->
            task.message = "Hello"
            task.recipient = "World"
        })
    }
}