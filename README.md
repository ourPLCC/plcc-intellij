# plcc-intellij
An Intellij platform plugin for [PLCC](https://dl.acm.org/doi/10.1145/2538862.2538922).

This is a project I, ([@abacef](https://github.com/abacef)) will be developing for my Masters project during Fall 2020.

## Install and Run
The plugin is not yet on the JetBrains Plugin Marketplace, so you cant install it through there, but hopefully it will be soon.

In order to run the plugin from source:
- Check out the repo
- Open it with Intellij
- Install the Grammar-Kit plugin if not installed already
- Right click on src/main/grammar/plcc.bnf and select 'Generate Parser Code'
- Right click on src/main/grammar/plcc.flex and select 'Run JFlex Generator'
- Create a Gradle Run Configuration that runs the Gradle task :runIde
- Clisk the run button to start an IDE instance with the plugin installed on it

## Features
Check out the [feature list](https://github.com/ourPLCC/plcc-intellij/wiki/Feature-List). Each link has a tutorial.
