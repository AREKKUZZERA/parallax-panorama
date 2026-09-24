# Parallax Panorama

A small client-side Fabric mod for Minecraft 26.2 that makes the main menu panorama subtly move with your mouse.

## Features

* Smooth mouse parallax for the main menu panorama.
* Framerate-independent smoothing.
* Adjustable effect strength.
* Configurable through Mod Menu.
* Client-side only.
* No gameplay changes.

## Configuration

With [Mod Menu](https://modrinth.com/mod/modmenu) installed, open:

**Mods → Parallax Panorama → Configure**

You can adjust **Mouse reaction** from **0% to 200%**.

* **0%** — disables the effect.
* **100%** — default strength.
* **200%** — maximum strength.

The configuration is stored in:

```text
config/parallaxpanorama.properties
```

## Requirements

* Minecraft 26.2
* Fabric Loader 0.19.3+
* Fabric API
* Java 25+
* Mod Menu 20.0.0+ *(optional, for configuration)*

## Building

Clone the repository and run:

```bash
./gradlew build
```

The compiled JAR will be available in:

```text
build/libs/
```
