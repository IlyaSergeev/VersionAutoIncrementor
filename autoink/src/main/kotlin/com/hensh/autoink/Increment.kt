package com.hensh.autoink

/**
 * Created by i-sergeev on 11/01/2019.
 */
class Increment constructor(val name: String) {

    var startBuildVersion = 1
        private set

    fun startBuildVersion(startBuildVersion: Int) {
        this.startBuildVersion = startBuildVersion
    }
}