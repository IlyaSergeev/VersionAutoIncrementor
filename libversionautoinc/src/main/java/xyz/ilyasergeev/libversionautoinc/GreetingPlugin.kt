package xyz.ilyasergeev.libversionautoinc

/**
 * Created by i-sergeev on 11/01/2019.
 */
class AutoIncrementorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val store = AutoIncrementStore(project)

        val extension = project.extensions.create(
            "autoIncrementor",
            AutoIncrementExtension::class.java,
            project.container(Increment::class.java),
            store
        )

        if (project.plugins.hasPlugin(AppPlugin::class.java)) {

            val app = project.extensions.getByType(AppExtension::class.java) ?: return

            app.applicationVariants.all { variant ->

                val increment = extension.increments
                    .find { it.variants.contains(variant.name) } ?: return@all

                val task = project.tasks.create(
                    "increment${increment.name.capitalize()}On${variant.name.capitalize()}",
                    VariantIncrementTask::class.java
                ) {

                    it.group = "Auto incrementor"

                    it.increment = increment
                    it.variant = variant
                    it.store = store
                    it.appExtension = app

                }

                variant.preBuild.dependsOn(task)

            }

        }

    }
}