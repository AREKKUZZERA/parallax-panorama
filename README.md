# Parallax Panorama

A small client-side Fabric mod for Minecraft 26.2 that makes the main menu panorama subtly follow the mouse cursor.

## Features

- Smooth mouse parallax for the main menu panorama.
- Framerate-independent smoothing.
- Configurable effect strength through Mod Menu.
- Client-side only.
- No gameplay changes.

## Configuration

Install [Mod Menu](https://modrinth.com/mod/modmenu), open **Mods → Parallax Panorama → Configure**, and adjust **Mouse reaction** from 0% to 200%.

100% is the original default strength. The setting is stored in `config/parallaxpanorama.properties`.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3+
- Fabric API
- Java 25+
- Mod Menu 20.0.0+ for the configuration screen

## Build

Use the Gradle wrapper once it has been generated for the project:

```bash
./gradlew build
```

The resulting JAR is placed in `build/libs/`.

## Icon

The Mod Menu/Fabric icon is loaded from:

```text
src/main/resources/assets/parallaxpanorama/icon.png
```

Use a square PNG, preferably 128×128 or 256×256. Replace that file with your own logo without changing `fabric.mod.json`.

## Author

**AREKKUZZERA**

## License

MIT
