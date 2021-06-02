# Faction Items

## Compiler le plugin

Pour compiler le projet, gradle est utilisé avec le plugin "shadowjar". Il suffit juste de lancer
un ``./gradle shadowjar`` (``.\gradlew shadowjar`` sous Windows). Le plugin au format ``.jar`` ce trouve dans le
dossier ``./build``.

## Stack Technique

* Spigot 1.8.8 (RC-1)
* Kotlin
* Gradle

## Documentation

Au niveau de la configuration, dans les chaines de caractères vous pouvez utiliser des variables de la
forme ``{{nom_de_la_variable}}``. Elles sont détaillées dans la documentation de chaque section

Pour les permissions le fichier plugin.yml contient toutes les descriptions.

#### Configuration global

````yaml
messages:
  # Variable disponible de manière global: player_name,
  # TODO: use global "messages.not_enough_permission" everywhere on code
  not_enough_permission: "Vous n'avez pas la permission d'executer cette commande"
````

### RandomTP (``/rtp [pseudo]``)

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

### Chunkbuster (``/factionitems give <pseudo> chunkbuster [amount]``)

Permet de casser un chunk jusqu'à la bedrock.

La commande give donne une frame de portail de l'end, qui, avec un clique droit à la fin d'un countdown va casser le
chunk jusqu'a la bedrock. Utilisable uniquement dans sa faction (sauf avec la permission)

La commande bust va détruire le chunk de coordonnée x et z.

#### Configuration

````yaml
chunkbuster:
  countdown: 4 # Exprimer en seconde
  messages:
    # Variable disponible de manière global: player_name, 
    not_enough_permission: Vous n'avez pas la permission d'executer cette commande
    not_enough_permission_to_place_it: Vous avez pas la permission de placer ce chunkbuster
    placeholder: Explosion dans §l§4{{countdown}}§r secondes
    already_active: §l§4Attention...§r Ce chunkbuster est déjà prêt à exploser
````

### Dynamite (``/factionitems give <pseudo> dynamite [amount] [radius] [fire]``)

Permet de tout casser dans un rayon donné.

#### Configuration

````yaml
dynamite:
  countdown: 2 # Exprimer en seconde
  power: 4 # La puissance d'une TNT est de 4
  fire: true
  messages:
    # Variable disponible de manière global: player_name, 
    not_enough_permission: Vous n'avez pas la permission d'executer cette dynamite
    placeholder: Explosion dans §l§4{{countdown}}§r secondes
````

### Pioche

#### Pioche Epique ``(/factionitems give <pseudo> pickaxe:epic [amount])``

- Efficiency 3; Unbreaking 3
- Cuit les minerais automatiquement
- Mine en 3x3

#### Pioche Légendaire ``(/factionitems give <pseudo> pickaxe:legendary [amount])``

- Efficiency 5; Unbreaking 3; Fortune 2
- Cuit les minerais automatiquement
- Mine en 5x5

### Epée

#### Epée Epique ``(/factionitems give <pseudo> sword:epic [amount])``

- Chatiment 5; Unbreaking 3; Looting 5

### Houe

#### Houe Epique ``(/factionitems give <pseudo> houe:epic [amount])``

- Accélération sur la soul sand
- Replentation auto en 3x3
- Unbreaking 3

#### Houe Légendaire ``(/factionitems give <pseudo> houe:legndary [amount])``

- Accélération sur la soul sand
- Replantation auto en 5x5
- Unbreaking