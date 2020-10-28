plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
}

group = "org.example"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.2"
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Initial release of the plugin. Minimal features are implemented so far.
      """)
    version("0.0.2")
}

sourceSets["main"].java.srcDirs("src/main/gen")
