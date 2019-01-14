package xyz.ilyasergeev.libversionautoinc

/**
 * Created by i-sergeev on 14/01/2019.
 */
class Increment constructor(val name : String)
{
    var variants : Array<String> = arrayOf()
        private set

    var versionCodeIncrement = 1
        private set

    var versionNameIncrement = "0.0.0"
        private set

    fun versionCodeIncrement(versionCodeIncrement : Int)
    {
        this.versionCodeIncrement = versionCodeIncrement
    }

    fun versionNameIncrement(versionNameIncrement : String)
    {
        this.versionNameIncrement = versionNameIncrement
    }

    fun onVariants(variants : Array<String>)
    {
        this.variants = variants
    }
}