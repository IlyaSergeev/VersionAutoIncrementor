package xyz.ilyasergeev.libversionautoinc

import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * Created by i-sergeev on 14/01/2019.
 */
class IncrementRule constructor(val name: String) {
    var startBuildVersion = 1

    var buildNumberStep = 1

    var applyPrefix = false

    var changeFileName = false

    fun startBuildVersion(startBuildVersion: Int) {
        this.startBuildVersion = startBuildVersion
    }

    fun buildNumberStep(buildNumberStep: Int) {
        this.buildNumberStep = buildNumberStep
    }

    fun applyPrefix(applyPrefix: Boolean) {
        this.applyPrefix = applyPrefix
    }

    fun changeFileName(changeFileName: Boolean) {
        this.changeFileName = changeFileName
    }
}