<p align="center">
  <a href="../../releases/latest"><img src="https://img.shields.io/github/v/release/Fulminazzo/JBukkit?display_name=tag&color=red" alt="Latest version" /></a>
  <a href="https://app.codacy.com/gh/Fulminazzo/JBukkit/"><img src="https://app.codacy.com/project/badge/Grade/245a80286391425d8f7fad220824c566" alt="Codacy Grade" /></a>
  <a href="https://app.codacy.com/gh/Fulminazzo/JBukkit/"><img src="https://tokei.rs/b1/github/Fulminazzo/JBukkit?category=code&style=flat" alt="Lines of Code" /></a>
</p>
<p align="center">
    <a href="../../commit/"><img src="https://img.shields.io/github/commits-since/Fulminazzo/JBukkit/1.0" alt="GitHub commits"/></a>
</p>

<p align="center">
    <img src="https://forthebadge.com/images/badges/code-sucks-it-works.svg" alt="">
    <img src="https://forthebadge.com/images/badges/pro-crastinatior.svg" alt="">
</p>

**JBukkit** is a **library** created for **unit testing [Bukkit](https://dev.bukkit.org/) related projects**.

It does so by providing many **implementations of Bukkit classes** with the help of [JUnit5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/).

## How to import
**JBukkit** can be imported using one of the three most common methods:
- **Gradle** (preferred):
  ```groovy
  repositories {
  	maven { url = 'https://repo.fulminazzo.it/releases' }
  }

  dependencies {
  	implementation 'it.fulminazzo:JBukkit:latest'
  }
  ```
- **Maven** (alternative):
  ```xml
  <repository>
  	<id>fulminazzo</id>
  	<url>https://repo.fulminazzo.it/releases</url>
  </repository>
  ```
  ```xml
  <dependency>
  	<groupId>it.fulminazzo</groupId>
  	<artifact>JBukkit</artifact>
  	<version>LATEST</version>
  </dependency>
  ```
- **Manual** (discouraged): download the JAR file from the [latest release](../../releases/latest) and import it using your IDE.