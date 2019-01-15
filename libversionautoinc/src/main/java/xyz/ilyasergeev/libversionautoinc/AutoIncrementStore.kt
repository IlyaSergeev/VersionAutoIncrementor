package xyz.ilyasergeev.libversionautoinc

import org.gradle.api.Project
import java.io.*
import java.util.*

/**
 * Created by i-sergeev on 14/01/2019.
 */
class AutoIncrementStore(private val project: Project) {
    companion object {
        private const val FIELD_BUILD_NUMBER = "BUILD_NUMBER"
    }

    var buildNumber: Int?
        get() = properties.buildNumber
        internal set(value) {
            properties.buildNumber = value
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

    private var Properties.buildNumber: Int?
        get() = this[FIELD_BUILD_NUMBER]?.toString()?.toIntOrNull()
        set(value) {
            this[FIELD_BUILD_NUMBER] = "$value"
        }

    private fun File.newWriter(): Writer {
        return BufferedWriter(FileWriter(this))
    }
}