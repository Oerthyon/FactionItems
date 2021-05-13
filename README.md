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

### Chunkbuster (``/chunkbuster give <pseudo>`` // ``/chunkbuster bust <x> <z>``)
Permet de casser un chunk jusqu'à la bedrock. 

La commande give donne une frame de portail de l'end, qui, avec un clique droit à la fin d'un countdown va casser le chunk jusqu'a la bedrock. Utilisable uniquement dans sa faction (sauf avec la permission)

La commande bust va détruire le chunk de coordonnée x et z.

#### Configuration

````yaml
chunkbuster:
  countdown: 4 # Exprimer en seconde
  messages:
    # Variable disponible de manière global: player_name, 
    not_enough_permission: "Vous n'avez pas la permission d'executer cette commande"
    not_enough_permission_to_place_it: "Vous avez pas la permission de placer ce chunkbuster"
    already_active: "§l§4Attention...§r Ce chunkbuster est déjà prêt à exploser"
````

#### Permission
* ``oerthyon.factionitems.chunkbuster.give`` donne la permission de donner le chunkbuster.
* ``oerthyon.factionitems.chunkbuster.use`` donne la permission d'utiliser cette chunkbuster.
* ``oerthyon.factionitems.chunknbuster.bust_command`` donne la permission d'utiliser ``/chunkbuster bust <x> <z>``.
* ``oerthyon.factionitems.chunkbuster.use_everywhere`` donne la permission d'utiliser un chunkbuster partout, même hors de sa faction.

### Dynamite (``/dynamite give <pseudo>``)
Permet de tout casse dans un rayon donné.

#### Configuration
* ``dynamite.radius`` permet de configurer le rayon d'explosion
* ``dynamite.damage`` permet de configurer les dégats reçu par les joueurs

#### Permission
* ``oerhtyon.factionitems.dynamite.give``: permission de donner a un joueur (ou soit même) une dynamite
* ``oerthyon.factionitems.dynamite.use``: permission d'utiliser une dynamite
