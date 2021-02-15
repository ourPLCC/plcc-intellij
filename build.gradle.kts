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
    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020+"
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
        Now compatible with all 2020 versions of Intellij.
      """)
    version("0.0.6")
}

sourceSets["main"].java.srcDirs("src/main/gen")
