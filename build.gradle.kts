import net.minecrell.gitpatcher.PatchExtension

plugins {
    id("net.minecraftforge.gitpatcher") version "0.10.+"
}

configure<PatchExtension> {
    submodule = "spark"
    patches = file("patches")
    target = file("patched-spark")
}

tasks.register("rebuildPatches") {
    dependsOn(tasks.makePatches)
}