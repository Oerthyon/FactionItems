# Faction Items

## Compiler le plugin
Pour compiler le projet, gradle est utiliser avec le plugin "shadowjar". Il suffit juste de lancer un ``./gradle shadowjar`` (``.\gradlew shadowjar`` sous Windows). Le plugin au format ``.jar`` ce trouve dans le dossier ``./build``.

## Stack Technique
* Spigot 1.8.8 (RC-1)
* Kotlin
* Gradle

## Documentation

### RandomTP (``/rtp``)
Permet de se téléporter aleatoirement sur la map.

#### Configuration
* ``random_tp.messages.before_tp``: le message afficher a la téléportation
* ``random_tp.cooldown``: permet de modifier le cooldown avant la téléportation

#### Permission
* ``oerthyon.factionitems.rtp`` octrois la permission de ce "randomtp"
* ``oerthyon.factionitems.rtp.no_cooldown`` permet de en pas avoir de cooldown sur la commande.

### Chunkbuster (``/chunkbuster give <pseudo>``)
Permet de casser un chunk un chunk jusqu'a la bedrock (laisse un trou jusqu'en couche ~=3)

#### Permission
* ``oerthyon.factionitems.chunkbuster.give`` donne la permission de donner a un joueur (et soit même) cette chunkbuster.
* ``oerthyon.factionitems.chunkbuster.use`` donne la permission d'utiliser cette chunkbuster.

### Dynamite (``/dynamite give <pseudo>``)
Permet de tout casse dans un rayon donné.

#### Configuration
* ``dynamite.radius`` permet de configurer le rayon d'explosion
* ``dynamite.damage`` permet de configurer les dégats reçu par les joueurs

#### Permission
* ``oerhtyon.factionitems.dynamite.give``: permission de donner a un joueur (ou soit même) une dynamite
* ``oerthyon.factionitems.dynamite.use``: permission d'utiliser une dynamite
