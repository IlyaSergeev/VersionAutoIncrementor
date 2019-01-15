package xyz.ilyasergeev.libversionautoinc

/**
 * Created by i-sergeev on 14/01/2019.
 */
class Increment constructor(val name : String)
{
    var startBuildVersion = 1
        private set

    var buildNumberStep = 1
        private set

    fun startBuildVersion(startBuildVersion : Int)
    {
        this.startBuildVersion = startBuildVersion
    }

    fun buildNumberStep(buildNumberStep : Int)
    {
        this.buildNumberStep = buildNumberStep
    }
}