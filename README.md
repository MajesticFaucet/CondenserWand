# CondenserWand (Unmaintained)
A leightweight sort and craft wand designed to run on your Spigot server.
## -DISCLAIMER-
This project is unmaintained, if you would like to continue development, feel free to fork it.
## Features
* WorldGuard integration
* Special item with configurable display-name and meta.
* Plugin compares player items to make sure they match displayname and meta defined in configuration file.
* Ability to use wand on chests, trapped chests, enderchests, droppers, dispensers, and minecart chests.
* Shiny item (optional enchanted effect).
* Cool effects when giving a player a wand.
* Toggable auto-sort feature.
* Permission based auto sort and wand usage.
* Highly configurable messages and features (almost all features are optional and can be turned on or off at any time in the configuration file).
## Upcoming Features
* Ability to work with additional containers such shulker boxes.
* 1.13 support coming soon!
* Integrations with more anti-grief plugins.
* Different sort modes (alphabetical, numeric, type).
## Supported Versions
CondenserWand should work on all versions of Spigot **_except for 1.13_**, although support is coming soon in the near future.
## Permissions
* condenserwand.use - access to use the wand.
* condenserwand.use.sort - access to use the sort feature toggable with `/condenserwand sort`.
## Commands
* `/condenserwand help` - displays the help message.
* `/condenserwand give` - gives a player a condenser wand, or if left blank gives it to yourself.
* `/condenserwand sort` - toggle sorting.
* `/condenserwand reload` - reloads the plugin.
* `/condenserwand version` - displays plugin version.
* `/condenserwand info` - displays plugin information.
## Required Dependencies
* None at all :)
## Downloading
You can either download or build from source.

If you don't want to build from source, just head over to the [releases](https://github.com/MajesticFaucet/CondenserWand/releases) page for the pre-built jar files.

## Building
To build the plugin, you first need Maven and Java 8 JDK.

After installed, clone the repository.
```
git clone https://github.com/MajesticFaucet/CondenserWand.git
```
Checkout the branch you want.
```
git checkout branch
```
Finally, have Maven build and package the jar file.
```
mvn clean install
```
## Contirbuting
All feedback is appreciated. Please report bugs to the [issues](https://github.com/MajesticFaucet/CondenserWand/issues) page and I will try to get to them as quickly as I can.
## Licensing
This is project is free software. Feel free to modify or distribute this software as long as it is in accordance with the GNU GPLv3.0 License.
