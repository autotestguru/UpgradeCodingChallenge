# Readme.md for Upgrade - Coding Challenge Automation Framework
================================================================
## Tools requirement
=====================
1. Java JDK 1.8+
2. Apache Maven 3.6.0 +
3. Make sure that required browsers are installed on the machine.
4. IDEA Intellij 2018.2 (or latest) community edition (optional)
5. All other framework dependencies are driven through maven build automation and dependencies management capabilities.

## Installation and configuration
==================================
1. Install required tools in the given sequence.
2. Setup JAVA_HOME and M2_HOME in Environment variables to locations of Java JDK and Maven binaries respectively.
3. Clone the Automation project from Github location, https://github.com/autotestguru/UpgradeCodingChallenge/tree/master (master branch)
5. Import the project into Intellij or desired IDE as a maven project or using console (cmd/terminal) navigate to base folder for framework.

## Executing Tests
===================
1. All tests can be ran using maven 'test' lifecycle.
2. For running UI tests, use command 'mvn clean test -Dtest=JunitUITests' on console (without the quotes)
2. For running API tests, use command 'mvn clean test -Dtest=JunitAPITests' on console (without the quotes)
