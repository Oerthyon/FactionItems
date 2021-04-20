# Faction Items

## Compiler le plugin
Pour compiler le projet, gradle est utilisé avec le plugin "shadowjar". Il suffit juste de lancer un ``./gradle shadowjar`` (``.\gradlew shadowjar`` sous Windows). Le plugin au format ``.jar`` ce trouve dans le dossier ``./build``.

## Stack Technique
* Spigot 1.8.8 (RC-1)
* Kotlin
* Gradle

## Documentation

Au niveau de la configuration, dans les chaines de caractères vous pouvez utiliser des variables de la forme ``{{nom_de_la_variable}}``. Elles sont détailer dans la documentation de chaque section

### RandomTP (``/rtp``)
Permet de se téléporter aléatoirement sur la map.

#### Configuration

````yaml
random_tp:
  cooldown: 10 # Exprimer en seconde
  overworld:
    min_x: -1000
    max_x: 1000
    min_z: -1000
    max_z: 1000
  nether:
    min_x: -1000
    max_x: 1000
    min_z: -1000
    max_z: 1000
  messages:
    # Variable disponible de manière global: player_name, 
    not_enough_permission: "Vous n'avez pas la permission d'executer cette commande"
    bad_dimension: "Vous devez être dans l'overworld ou le nether pour utiliser cette commande"
    unable_to_find_location: "Impossible de trouver un endroit ou vous téléporter"
    cooldown_unfinished: "Veuillez attendre {{cooldown_remaining}} secondes" # Ici, la variable cooldown_remaining peut être utiliser 
  max_iteration_before_finding_position: 100000
````

#### Permission
* ``oerthyon.factionitems.rtp`` octrois la permission de ce "randomtp"
* ``oerthyon.factionitems.rtp.no_cooldown`` permet de en pas avoir de cooldown sur la commande.

### Chunkbuster (``/chunkbuster give <pseudo>``)
Permet de casser un chunk un chunk jusqu'à la bedrock (laisse un trou jusqu'en couche ~=3)

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
