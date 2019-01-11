package xyz.ilyasergeev.libversionautoinc

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