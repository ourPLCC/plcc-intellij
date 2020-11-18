plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    id("io.freefair.lombok") version "5.3.0"
}

group = "org.example"
version = "0.0.3"

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
        Added support for defining java code blocks in a plcc file
        Added support for .ijava files (files with java code blocks corresponding to a grammar rule) with syntax recognition (and syntax errors are caught)
        There is now a plugin icon
      """)
    version("0.0.3")
}

sourceSets["main"].java.srcDirs("src/main/gen")
