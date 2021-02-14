plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    id("io.freefair.lombok") version "5.3.0"
}

group = "org.example"
version = "0.0.4"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.2+"
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
        Fixed some bugs in specifying a PLCC installation in the Project Setup Wizard
        Fixed Java code not being recognised in a Java code block for plcc and ijava files 
        Fixed include file REGEX in a plcc file to recognise files with a file extension
      """)
    version("0.0.4")
}

sourceSets["main"].java.srcDirs("src/main/gen")
