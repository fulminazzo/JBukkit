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

| Table of contents                               |
|-------------------------------------------------|
| [How to import](#how-to-import)                 |
| [How to choose module version](#version-choice) |
| [How to use](#how-to-use)                       |

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

## Version choice
**JBukkit** provides one version for **every Minecraft version from 1.8** to the latest.
To choose the correct one, the **second leading number** should be used as reference for the modules.

So, for example, when importing for **Minecraft 1.13**:
- **Gradle** (preferred):
  ```groovy
  dependencies {
  	implementation 'it.fulminazzo.JBukkit:13:latest'
  }
  ```
- **Maven** (alternative):
  ```xml
  <dependency>
  	<groupId>it.fulminazzo.JBukkit</groupId>
  	<artifact>13</artifact>
  	<version>LATEST</version>
  </dependency>
  ```
  
**NOTE:** every module uses as reference **the latest patch** of its version.
This means that module `13` is compatible with Minecraft `1.13.2`, 
but **might not support** Minecraft `1.13` and `1.13.2`.

## How to use
After [importing the project](#how-to-import) with the [appropriate version](#version-choice), 
it is possible to create a **JUnit test**.

The main class of reference for **JBukkit** is 
[BukkitUtils](../../tree/master/base/src/main/java/it/fulminazzo/jbukkit/BukkitUtils.java) 
which provides various methods:

- `setUp` (**mandatory for proper functioning**): it verifies the **Minecraft version in use**.
  This method will also use the annotations 
  [Before1_](../../tree/master/base/src/main/java/it/fulminazzo/jbukkit/annotations/Before1_.java) and
  [After1_](../../tree/master/base/src/main/java/it/fulminazzo/jbukkit/annotations/After1_.java) 
  to check if the **current version** is, **respectively**, **before** or **after** the specified versions.
  If it is not, the **annotated tests will be skipped**;
- `setupServer`: initializes the `Bukkit#getServer` object with various parameters 
  (loggers, recipes, support for inventories creation, players list and more).
  In **Minecraft 1.20+** it **initializes the registries**, **mandatory** for certain classes;
- `setupEnchantments`: initializes all the **enchantments** found in the 
  [Bukkit Enchantment class](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html).
  The initialization method and the parameters will vary from version to version;
- various 
  [Player](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/Player.html) and 
  [OfflinePlayer](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/OfflinePlayer.html) 
  methods to **add**, **remove** or **get** players.
  
  **NOTE:** at the time of writing, adding a player **will not trigger PlayerJoinEvent**.

To use
[BukkitUtils](../../tree/master/base/src/main/java/it/fulminazzo/jbukkit/BukkitUtils.java) 
it is possible to **extend it** from the **testing class** and calling the **super method** `setUp`:
```java
import it.fulminazzo.jbukkit.BukkitUtils;

class PluginTest extends BukkitUtils {
    
    @BeforeEach
    void setUp() {
        super.setUp();
    }
    
}
```

**JBukkit** offers the possibility to create **many Bukkit API classes and interfaces**,
using **mock versions of them**, in order to **replicate** the expected behaviour in a real server environment.

To check if such a class exists for the target type, simply add "_Mock_" in front of the type name.
For example, for [Inventory](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/Inventory.html)
the [MockInventory](../../tree/master/base/src/main/java/it/fulminazzo/jbukkit/inventory/MockInventory.java) 
class is found.

**NOTE:** many of the mocks are **version dependent**, therefore they **might change** for each module.
