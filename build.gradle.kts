plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    id("io.freefair.lombok") version "5.3.0"
}

group = "org.example"
version = "0.0.6"

repositories {
    mavenCentral()
}

dependencies {

}

// See https://github.com/JetBrains/gradle-intellij-plugin/
// this is the settings for the test instance that comes up when :runIde is called
intellij {

}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
        Now compatible with all 2020 versions of Intellij.
    """)
    version(version)
}

sourceSets["main"].java.srcDirs("src/main/gen")
