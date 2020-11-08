# plcc-intellij
An Intellij platform plugin for [PLCC](https://dl.acm.org/doi/10.1145/2538862.2538922).

This is a project I, ([@abacef](https://github.com/abacef)) will be developing for my Masters project during Fall 2020.

## Features
Check out the [feature list](https://github.com/ourPLCC/plcc-intellij/wiki/Feature-List). Each link has a tutorial.

## Install and Run

### From Jetbrains Marketplace
Search for PLCC

### From Source
- Check out this repo
- Open it with Intellij, wait for Gradle project to be imported
- Install the Grammar-Kit plugin if not installed already
- Right click on the following files and select 'Generate Parser Code'
  - src/main/grm_lex/plcc/plcc.bnf
  - src/main/grm_lex/ijava/ijava.bnf
- Right click on the following files and select 'Run JFlex Generator'. If this is your first time running the JFlex generator, you may be prompted for a directory to install JFlex in. Select the project's root directory.
  - src/main/grammar/plcc/plcc.flex
  - src/main/grammar/ijava/plcc.flex
- Create a Gradle Run Configuration that runs the Gradle task :runIde
- Click the run button to start an IDE instance with the plugin installed on it. You may have to wait for a Intellij instance to download from the internet which may take a while.
