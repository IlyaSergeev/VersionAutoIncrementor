package com.hensh.autoink

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask



/**
 * Created by i-sergeev on 11/01/2019.
 */
open class Greeting : DefaultTask() {
    var message: String? = null
    var recipient: String? = null

    @TaskAction
    internal fun sayGreeting() {
        System.out.printf("%s, %s!\n", message, recipient)
    }
}