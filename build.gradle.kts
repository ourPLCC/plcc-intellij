plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    id("io.freefair.lombok") version "5.3.0"
}

group = "org.example"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {

}

// See https://github.com/JetBrains/gradle-intellij-plugin/
// this is the settings for the test instance that comes up when :runIde is called
intellij {
    updateSinceUntilBuild = false
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
        A run configuration can be automatically created by clicking the green run button that shows up in the gutter of an open .plcc file
        The plugin now tells you where and what version of PLCC is being downloaded
        The .plcc file to run is now autodetected when manually creating a run configuration.
        The plugin is now compatible with all versions of intellij after 2020.2
    """)
    version(version)
}

sourceSets["main"].java.srcDirs("src/main/gen")
