pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://files.minecraftforge.net/maven/")
    }
}

if (file("patched-spark").exists()) {
    includeBuild("patched-spark")
}

rootProject.name = "cloudnet-spark-fork"

