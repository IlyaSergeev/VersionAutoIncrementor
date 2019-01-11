package com.hensh.autoink

import org.gradle.api.Project
import java.io.*
import java.util.*

/**
 * Created by i-sergeev on 11/01/2019.
 */
class AutoIncrementStore(private val project: Project) {

    var versionCode: Int
        get() = properties.versionCode ?: 1
        internal set(value) {
            properties.versionCode = value
            commit()
        }

    var versionName: String
        get() = properties.versionName ?: "0.0.1"
        internal set(value) {
            properties.versionName = value
            commit()
        }

    private val properties = getProps()

    private fun commit() {
        properties.store(getPropsFile().newWriter(), null)
    }

    private fun getProps(): Properties {
        return Properties().apply {
            load(FileInputStream(getPropsFile()))
        }
    }

    private fun getPropsFile(): File {
        return project.file("autoIncrementor.properties").apply {
            if (!exists()) {
                parentFile.mkdirs()
                createNewFile()
            }
        }
    }

    //region// Extensions

    private var Properties.versionCode: Int?
        get() = this["VERSION_CODE"]?.toString()?.toIntOrNull()
        set(value) {
            this["VERSION_CODE"] = value.toString()
        }

    private var Properties.versionName: String?
        get() = this["VERSION_NAME"]?.toString()
        set(value) {
            this["VERSION_NAME"] = value
        }

    private fun File.newWriter(): Writer {
        return BufferedWriter(FileWriter(this))
    }

    //endregion

}